package com.mahaexam.tenant.management.service.bulkservice.student;

import com.mahaexam.common.config.S3Service;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.model.Course;
import com.mahaexam.common.model.SubjectGroup;
import com.mahaexam.common.service.ClassService;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.service.CourseService;
import com.mahaexam.common.service.SubjectGroupService;
import com.mahaexam.packagemanagment.model.StudentPackageMapping;
import com.mahaexam.packagemanagment.service.StudentPackageMappingService;
import com.mahaexam.tenant.management.bean.StudentDataLoadBean;
import com.mahaexam.tenant.management.bean.StudentRegistrationBean;
import com.mahaexam.tenant.management.service.ApplicationUserService;
import com.mahaexam.tenant.management.service.StudentService;
import com.mahaexam.tenant.management.service.bulkservice.UploadProcessor;
import com.mahaexam.tenant.management.service.bulkservice.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentUploadProcessor implements UploadProcessor<StudentDataLoadBean> {
    private static final Logger logger = LogManager.getLogger(StudentUploadProcessor.class);
    private final StudentService studentService;
    private final ApplicationUserService applicationUserService;
    private final ClassService classService;
    private final SubjectGroupService subjectGroupService;
    private final ConfigService configService;
    private final S3Service s3Service;
    private final CourseService courseService;
    private final StudentPackageMappingService studentPackageMappingService;
//    private final StudentPackageSelectionRepository studentPackageSelectionRepository;
//    private final StudentPackageSelectionSummaryRepository studentPackageSelectionSummaryRepository;

    public StudentUploadProcessor(StudentService studentService, ApplicationUserService applicationUserService,
                                  ClassService classService, SubjectGroupService subjectGroupService,
                                  ConfigService configService, S3Service s3Service, CourseService courseService,
//                                  StudentPackageSelectionRepository studentPackageSelectionRepository, StudentPackageSelectionSummaryRepository studentPackageSelectionSummaryRepository,
                                  StudentPackageMappingService studentPackageMappingService) {
        this.studentService = studentService;
        this.applicationUserService = applicationUserService;
        this.classService = classService;
        this.subjectGroupService = subjectGroupService;
        this.configService = configService;
        this.s3Service = s3Service;
        this.courseService = courseService;
//        this.studentPackageSelectionRepository = studentPackageSelectionRepository;
//        this.studentPackageSelectionSummaryRepository = studentPackageSelectionSummaryRepository;
        this.studentPackageMappingService = studentPackageMappingService;
    }

    @Override
    public List<StudentDataLoadBean> readExcelToEntities(String filePath) throws IOException {
        List<StudentDataLoadBean> students = new ArrayList<>();
        var configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
        var config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
        String s3BucketName = config.getValue();
        byte[] fileBytes = s3Service.getFile(s3BucketName, filePath);

        try (InputStream is = new ByteArrayInputStream(fileBytes);
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                return students; // empty sheet
            }

            Row headerRow = rows.next();
            Map<String, Integer> headers = new HashMap<>();
            for (Cell cell : headerRow) {
                headers.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                StudentDataLoadBean s = new StudentDataLoadBean();
                s.setLastName(getCellValueSafe(row, headers, "last name"));
                s.setFirstName(getCellValueSafe(row, headers, "first name"));
                s.setMiddleName(getCellValueSafe(row, headers, "middle name"));
                s.setAdharNo(getCellValueSafe(row, headers, "adhar no"));
                s.setMobileNumber(getCellValueSafe(row, headers, "mobile number"));
                s.setEmail(getCellValueSafe(row, headers, "email"));
                s.setClassName(getCellValueSafe(row, headers, "class"));
                s.setExamGroup(getCellValueSafe(row, headers, "exam group"));
                s.setCourses(getCellValueSafe(row, headers, "courses"));
                String targetYear = getCellValueSafe(row, headers, "target year");
                if (!targetYear.isEmpty()) {
                    try {
                        s.setTargetFinalExamYear(Integer.parseInt(targetYear));
                    } catch (NumberFormatException e) {
                        s.setTargetFinalExamYear(null); // Set null for invalid data
                    }
                } else {
                    s.setTargetFinalExamYear(null); // Set null for empty data
                }
                students.add(s);
            }
        }
        return students;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private String getCellValueSafe(Row row, Map<String, Integer> headers, String headerName) {
        Integer columnIndex = headers.get(headerName.trim().toLowerCase());
        if (columnIndex == null) {
            return ""; // Return empty string if header not found
        }
        if (row == null) return "";
        short lastCell = row.getLastCellNum();
        if (lastCell < 0 || columnIndex < 0 || columnIndex >= lastCell) {
            return ""; // Column not present in this row
        }
        Cell cell = row.getCell(columnIndex);
        return getCellValue(cell);
    }

    // Helper to safely truncate strings without throwing StringIndexOutOfBoundsException
    private String safeTruncate(String value, int maxLength) {
        if (value == null) return null;
        value = value.trim();
        return value.length() <= maxLength ? value : value.substring(0, maxLength);
    }

    @Override
    public ValidationResult<StudentDataLoadBean> validateEntities(List<?> students) {
        List<StudentDataLoadBean> valid = new ArrayList<>();
        List<StudentDataLoadBean> invalid = new ArrayList<>();
        Set<String> mobileNumbers = new HashSet<>();
        Set<String> emailAddresses = new HashSet<>();
        for (Object obj : students) {
            StudentDataLoadBean s = (StudentDataLoadBean) obj;
            StringBuilder errors = new StringBuilder();

            // Check for required fields first
            if (s.getFirstName() == null || s.getFirstName().trim().isEmpty()) {
                errors.append("First Name is required; ");
            }
            s.setFirstName(safeTruncate(s.getFirstName(), 100)); // Truncate safely to 100 characters


            if (s.getLastName() == null || s.getLastName().trim().isEmpty()) {
                errors.append("Last Name is required;");
            }
            s.setLastName(safeTruncate(s.getLastName(), 100)); // Truncate safely to 100 characters

            if (!(s.getAdharNo() == null || s.getAdharNo().trim().isEmpty()) && !s.getAdharNo().matches("\\d{12}")) {
                errors.append("Aadhar No must be 12 digits;");
            }
            s.setAdharNo(safeTruncate(s.getAdharNo(), 12)); // Truncate safely to 12 characters

            if (s.getMobileNumber() == null || s.getMobileNumber().trim().isEmpty() || !s.getMobileNumber().matches("\\d{10}")) {
                errors.append("Mobile Number must be 10 digits;");
            }else{
                if(s.getMobileNumber().length()>10){
                    errors.append("Mobile Number must be 10 digits;");
                }
                s.setMobileNumber(safeTruncate(s.getMobileNumber(), 10)); // Truncate safely to 10 characters
            }

            if (s.getEmail() == null || s.getEmail().trim().isEmpty() || !s.getEmail().contains("@")){
                errors.append("Email invalid;");
            }else {
                if(s.getEmail().length()>255){
                    errors.append("Email must be less than 255 characters;");
                }
                s.setEmail(safeTruncate(s.getEmail(), 255)); // Truncate safely
            }

            if (s.getClassName() == null || s.getClassName().trim().isEmpty()) {
                errors.append("Class is required;");
            }else {
                s.setClassName(safeTruncate(s.getClassName(), 100)); // Truncate safely to 100 characters
            }

            if (s.getExamGroup() == null || s.getExamGroup().trim().isEmpty()) {
                errors.append("Exam Group is required; ");
            }else {
                s.setExamGroup(safeTruncate(s.getExamGroup(), 100)); // Truncate safely to 100 characters
            }

            if (s.getCourses() == null || s.getCourses().trim().isEmpty()) {
                errors.append("Courses are required; ");
            }else{
                s.setCourses(safeTruncate(s.getCourses(), 800)); // Truncate safely to 500 characters
            }

//            if (Objects.isNull(s.getTargetFinalExamYear()) || s.getTargetFinalExamYear() <= LocalDate.now().getYear()) {
//                errors.append("Target Final Exam Year is required and must be in the future; ");
//            }

            // Check for duplicates only if basic validation passes
            if (!s.getMobileNumber().trim().isEmpty() && applicationUserService.findByMobileNo(s.getMobileNumber()).isPresent())
                errors.append("Mobile Number already exists; ");

            if (!s.getEmail().trim().isEmpty() && applicationUserService.findByEmailId(s.getEmail()).isPresent())
                errors.append("Email already exists; ");

            // Validate class and exam group
            if (!s.getClassName().trim().isEmpty()) {
                Optional<ClassEntity> optionalClassEntity = classService.findClassByName(s.getClassName());
                if (optionalClassEntity.isPresent()) {
                    s.setClassId(optionalClassEntity.get().getId());
                } else {
                    errors.append("Class '" + s.getClassName() + "' not found; ");
                }
            }

            if (!s.getExamGroup().trim().isEmpty()) {
                Optional<SubjectGroup> subjectGroupOptional = subjectGroupService.findGroupByName(s.getExamGroup());
                if (subjectGroupOptional.isPresent()) {
                    s.setSubjectGroupId(subjectGroupOptional.get().getGroupId());
                } else {
                    errors.append("Exam Group '" + s.getExamGroup() + "' not found; ");
                }
            }

            // Validate courses
            if (!s.getCourses().trim().isEmpty()) {
                String[] coursesNames = s.getCourses().split(",");
                List<Course> courses = courseService.findByNames(Arrays.stream(coursesNames).map(String::trim).toList());
                if (courses != null && !courses.isEmpty() && courses.size() == coursesNames.length) {
                    s.setCourseIds(courses.stream().map(c -> Long.valueOf(c.getId())).collect(Collectors.toList()));
                } else {
                    errors.append("One or more courses not found: " + s.getCourses() + "; ");
                }
            }
            if(!mobileNumbers.add(s.getMobileNumber())){
                errors.append("Duplicate Mobile Number in the file; ");
            }
            if(!emailAddresses.add(s.getEmail())) {
                errors.append("Duplicate Email Address in the file; ");
            }
            if (!errors.isEmpty()) {
                s.setErrorMessage(errors.toString());
                invalid.add(s);
            } else {
                valid.add(s);
            }
        }
        ValidationResult<StudentDataLoadBean> vr = new ValidationResult<>();
        vr.setValidEntities(valid);
        vr.setInvalidEntities(invalid);
        return vr;
    }

    @Override
    public void insertValidEntities(List<?> validEntities, boolean withPayment) {
        StudentRegistrationBean registrationDTO;
        for (Object obj : validEntities) {
            StudentDataLoadBean s = (StudentDataLoadBean) obj;
            registrationDTO = new StudentRegistrationBean();
            registrationDTO.setAadharNumber(s.getAdharNo());
            registrationDTO.setFirstName(s.getFirstName());
            registrationDTO.setMiddleName(s.getMiddleName());
            registrationDTO.setLastName(s.getLastName());
            registrationDTO.setEmail(s.getEmail());
            registrationDTO.setRegisteredMobileNumber(s.getMobileNumber());
            registrationDTO.setCourses(s.getCourseIds());
            registrationDTO.setClassId(s.getClassId());
            registrationDTO.setSubjectGroupId(s.getSubjectGroupId());
            registrationDTO.setTargetFinalExamYear(s.getTargetFinalExamYear());
            registrationDTO.setPassword(s.getMobileNumber());
            registrationDTO.setReTypePassword(s.getMobileNumber());
            registrationDTO.setStudentReferenceId(s.getReferenceId());
            registrationDTO.setMedium(s.getMedium());
            Long studentId = studentService.registerStudent(registrationDTO, false,false, true);
            registrationDTO.setStudentId(studentId);
            s.setStudentId(studentId);

            List<StudentPackageMapping> mappings = new ArrayList<>();

            // Assign Default Package
            ClassEntity classEntity = classService.getClassById(registrationDTO.getClassId());
            String classWisePackageConigName = ConfigService.DEFAULT_PACKAGE + "_" + classEntity.getClassName();
            Optional<Config> configOpt = configService.findByName(classWisePackageConigName);
            Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "S3 Bucket Name Not Found : " + classWisePackageConigName));
            Integer packageId = Integer.parseInt(config.getValue());
            Set<Integer> packageIds = new HashSet<>();
            if(withPayment) {
                packageIds.add(s.getPackageId());
            }
            if(packageId>0) {
                packageIds.add(packageId);
            }
            logger.info("Assigning Packages {} to Student ID: {}", packageIds, studentId);
            packageIds.forEach(p->{
                mappings.add(StudentPackageMapping.builder()
                        .packageId(p)
                        .studentId(studentId)
                        .status(AppConstants.PACKAGE_STATUS_ACTIVE)
                        .createdDate(LocalDateTime.now())
                        .build());

            });
            studentPackageMappingService.saveMultiple(mappings);
            if(withPayment) {
                //Make Package Payment Entry in Student Package Selection Tables

            }
        }
    }

    @Override
    public String generateErrorFile(List<?> invalidEntities, Long batchId) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Error Rows");

        Row headerRow = sheet.createRow(0);
        String[] headers = new String[]{"Last Name", "First Name", "Middle Name", "Adhar No", "Mobile Number", "Email", "Class", "Exam Group", "Courses", "Target Year", "Error Message"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Object obj : invalidEntities) {
            StudentDataLoadBean s = (StudentDataLoadBean) obj;
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(s.getLastName());
            row.createCell(1).setCellValue(s.getFirstName());
            row.createCell(2).setCellValue(s.getMiddleName());
            row.createCell(3).setCellValue(s.getAdharNo());
            row.createCell(4).setCellValue(s.getMobileNumber());
            row.createCell(5).setCellValue(s.getEmail());
            row.createCell(6).setCellValue(s.getClassName());
            row.createCell(7).setCellValue(s.getExamGroup());
            row.createCell(8).setCellValue(s.getCourses());
            row.createCell(9).setCellValue(s.getTargetFinalExamYear());
            row.createCell(10).setCellValue(s.getErrorMessage());
        }

        String errorFileName = "error_batch_" + batchId + ".xlsx";

        var configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
        var config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
        String s3BucketName = config.getValue();

        configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER);
        config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "Package Folder NoT Found : " + ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER));
        String bulkUploadFolderName = config.getValue();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] excelBytes = baos.toByteArray();
        workbook.close();

        s3Service.uploadFile(s3BucketName, bulkUploadFolderName, errorFileName,
                excelBytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return bulkUploadFolderName + "/" + errorFileName;
    }

    @Override
    public String getSupportedClass() {
        return "Student";
    }

    /**
     * Read Excel from MultipartFile directly - reads into memory immediately to avoid temp file deletion issues
     */
    @Override
    public List<StudentDataLoadBean> readExcelToEntitiesV1(MultipartFile file) throws IOException {
        // Read file into byte array immediately to avoid temporary file deletion issues
        byte[] fileBytes = file.getBytes();
        return readExcelToEntitiesV1(fileBytes);
    }

    @Override
    public List<StudentDataLoadBean> readExcelToEntitiesV1(byte[] fileBytes) throws IOException {
        List<StudentDataLoadBean> students = new ArrayList<>();

        try (InputStream is = new ByteArrayInputStream(fileBytes);
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                return students; // empty sheet
            }

            Row headerRow = rows.next();
            Map<String, Integer> headers = new HashMap<>();
            DataFormatter dataFormatter = new DataFormatter();
            for (Cell cell : headerRow) {
                if (cell == null) continue;
                String headerName = dataFormatter.formatCellValue(cell);
                if (headerName == null) continue;
                headerName = headerName.trim().toLowerCase();
                if (!headerName.isEmpty()) {
                    headers.put(headerName, cell.getColumnIndex());
                }
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                StudentDataLoadBean s = new StudentDataLoadBean();
                s.setLastName(getCellValueSafe(row, headers, "last name"));
                s.setFirstName(getCellValueSafe(row, headers, "first name"));
                s.setMiddleName(getCellValueSafe(row, headers, "middle name"));
                s.setAdharNo(getCellValueSafe(row, headers, "adhar no"));
                s.setMobileNumber(getCellValueSafe(row, headers, "mobile number"));
                s.setEmail(getCellValueSafe(row, headers, "email"));
                s.setClassName(getCellValueSafe(row, headers, "class"));
                s.setExamGroup(getCellValueSafe(row, headers, "exam group"));
                s.setCourses(getCellValueSafe(row, headers, "courses"));
                String targetYear = getCellValueSafe(row, headers, "target year");
                if (!targetYear.isEmpty()) {
                    try {
                        s.setTargetFinalExamYear(Integer.parseInt(targetYear));
                    } catch (NumberFormatException e) {
                        s.setTargetFinalExamYear(null); // Set null for invalid data
                    }
                } else {
                    s.setTargetFinalExamYear(null); // Set null for empty data
                }
                students.add(s);
            }
        }
        return students;
    }
}
