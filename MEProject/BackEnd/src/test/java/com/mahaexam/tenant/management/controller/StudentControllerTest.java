package com.mahaexam.tenant.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.tenant.management.bean.*;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.tenant.management.bean.EnrollmentByExamBean;
import com.mahaexam.tenant.management.bean.StudentJourneyBean;
import com.mahaexam.tenant.management.bean.CompletedExamDetailsBean;
import com.mahaexam.tenant.management.service.ApplicationUserProfileService;
import com.mahaexam.tenant.management.service.ApplicationUserService;
import com.mahaexam.tenant.management.service.StudentService;
import com.mahaexam.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private ApplicationUserProfileService profileService;

    @Mock
    private ApplicationUserService applicationUserService;

    private StudentController studentController;

    private ObjectMapper objectMapper;
    private UserBean testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserBean();
        testUser.setUserId(1L);
        testUser.setTenantId(1L);

        // Use spy to mock inherited BaseController methods like getUser()
        studentController = Mockito.spy(new StudentController(studentService, profileService, applicationUserService));
        
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    private void mockUserSession() {
        doReturn(testUser).when(studentController).getUser();
    }

    @Test
    void testFindById_Success() throws Exception {
        Long userId = 1L;
        StudentDetailsBean student = new StudentDetailsBean();
        student.setStudentId(101L);
        student.setUserId(userId);
        student.setFirstName("John");

        when(studentService.findByIdFull(userId)).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(101))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        Long userId = 1L;
        when(studentService.findByIdFull(userId)).thenReturn(null);

        mockMvc.perform(get("/api/v1/students/{userId}", userId))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGetStudentImageById_Success() throws Exception {
        Long userId = 1L;
        StudentDetailsBean student = new StudentDetailsBean();
        student.setStudentId(101L);
        student.setPhotoImg("base64data");

        when(studentService.getStudentImageById(userId)).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/{studentUserId}/image", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoImg").value("base64data"));
    }

    @Test
    void testSearch_Success() throws Exception {
        mockUserSession();
        StudentSerchBean searchBean = new StudentSerchBean();
        searchBean.setStudentId(101L);

        PaginatedResponse<StudentDetailsBean> response = PaginatedResponse.<StudentDetailsBean>builder()
                .content(Collections.singletonList(new StudentDetailsBean()))
                .totalElements(1L)
                .build();

        when(studentService.search(any(UserBean.class), any(StudentSerchBean.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchBean)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void testUpdate_Success() throws Exception {
        Long studentId = 101L;
        StudentDetailsBean student = new StudentDetailsBean();
        student.setStudentId(studentId);
        student.setFirstName("Updated John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setRegisteredMobileNumber("9876543210");
        student.setClassId(10);
        student.setSubjectGroupId(1);
        student.setTargetFinalExamYear(2025);

        when(studentService.update(any(StudentDetailsBean.class))).thenReturn(student);

        mockMvc.perform(put("/api/v1/students/{studentId}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated John"));
    }

    @Test
    void testGetActiveStudentsWithPaidPackage_Success() throws Exception {
        mockUserSession();
        PaginatedResponse<StudentDetailsBean> response = PaginatedResponse.<StudentDetailsBean>builder()
                .content(Collections.emptyList())
                .totalElements(0L)
                .build();

        when(studentService.getActiveStudentsWithPaidPackage(anyLong(), any(), any(), any(), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/students/active-with-packages")
                .param("academicYear", "2024")
                .param("boardId", "1")
                .param("days", "30"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTotalExamCount_Success() throws Exception {
        mockUserSession();
        when(studentService.getTotalExamCount(anyLong(), any(), any(), any())).thenReturn(10);

        mockMvc.perform(get("/api/v1/students/total-exams/count")
                .param("academicYear", "2024")
                .param("boardId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testGetCompletedExamDetails_Success() throws Exception {
        mockUserSession();
        PaginatedResponse response = PaginatedResponse.builder().content(Collections.emptyList()).build();
        when(studentService.getCompletedExamDetails(anyLong(), any(), any(), any(), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/students/completed-exams")
                .param("academicYear", "2024")
                .param("boardId", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddStudent_Success() throws Exception {
        mockUserSession();
        StudentRegistrationBean registrationDTO = new StudentRegistrationBean();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john.doe@example.com");
        registrationDTO.setRegisteredMobileNumber("9876543210");
        registrationDTO.setClassId(10);
        registrationDTO.setSubjectGroupId(1);
        registrationDTO.setTargetFinalExamYear(2025);

        mockMvc.perform(post("/api/v1/students/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void testGetStudentPackageCount_Success() throws Exception {
        Long userId = 1L;
        when(studentService.getStudentPackageCount(userId)).thenReturn(5);

        mockMvc.perform(get("/api/v1/students/{userId}/package-count", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(5));
    }

    @Test
    void testGetEnrollmentByExamCount_Success() throws Exception {
        mockUserSession();
        List<EnrollmentByExamBean> enrollmentList = Arrays.asList(
                EnrollmentByExamBean.builder().examName("Exam 1").studentCount(50L).build()
        );
        when(studentService.getStudentEnrollmentByExam(anyLong(), any(), any(), any())).thenReturn(enrollmentList);

        mockMvc.perform(get("/api/v1/students/enrollment-by-exam/count")
                .param("academicYear", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].examName").value("Exam 1"))
                .andExpect(jsonPath("$[0].studentCount").value(50));
    }

    @Test
    void testGetStudentJourneyStats_Success() throws Exception {
        mockUserSession();
        List<StudentJourneyBean> journeyList = Arrays.asList(
                StudentJourneyBean.builder().groupName("Package A").totalExams(10).completedExams(5).build()
        );
        when(studentService.getStudentJourneyStats(anyLong(), any(), any(), any())).thenReturn(journeyList);

        mockMvc.perform(get("/api/v1/students/journey")
                .param("academicYear", "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("Package A"));
    }
}
