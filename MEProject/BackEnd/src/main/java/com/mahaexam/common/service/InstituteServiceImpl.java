package com.mahaexam.common.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.InstituteBean;
import com.mahaexam.common.bean.KeywordSearchRequest;
import com.mahaexam.common.model.Institute;
import com.mahaexam.common.repo.InstituteRepository;

@Service
public class InstituteServiceImpl implements InstituteService {

	@Autowired
	private InstituteRepository repository;

	@Override
	public int createInstitute(InstituteBean bean) {
		if (repository.existsByInstituteName(bean.getInstituteName())) {
			throw new ValidationException("Institute name already exists.");
		}
		Institute i = toEntity(bean);
		i.setCreatedAt(LocalDateTime.now());
		return repository.save(i);
	}

	@Override
	public Institute getInstituteById(int id) {
		return repository.findById(id);
	}

	@Override
	public List<Institute> getAllInstitutes() {
		return repository.findAll();
	}

	@Override
	public int updateInstitute(int id, InstituteBean bean) {
		if (repository.existsByInstituteNameExceptId(bean.getInstituteName(), id)) {
			throw new ValidationException("Institute name already exists.");
		}
		Institute i = toEntity(bean);
		i.setId(id);
		i.setUpdatedAt(LocalDateTime.now());
		return repository.update(i);
	}

	@Override
	public int deleteInstitute(int id) {
		return repository.delete(id);
	}

	private Institute toEntity(InstituteBean b) {
		Institute i = new Institute();
		i.setIndexNumber(b.getIndexNumber());
		i.setUdiNumber(b.getUdiNumber());
		i.setInstituteName(b.getInstituteName());
		i.setInstituteAddessLine1(b.getInstituteAddessLine1());
		i.setInstituteAddessLine2(b.getInstituteAddessLine2());
		i.setPlace(b.getPlace());
		i.setLatitude(b.getLatitude());
		i.setLongitude(b.getLongitude());
		i.setPinCode(b.getPinCode());
		i.setTelephone(b.getTelephone());
		i.setMobileNumber(b.getMobileNumber());
		i.setEmailId(b.getEmailId());
		i.setIntakeCapacity(b.getIntakeCapacity());
		i.setIntakeCapacity12th(b.getIntakeCapacity12th());
		i.setStaffDetails(b.getStaffDetails());
		i.setDigiInfraAvailability(b.getDigiInfraAvailability());
		i.setBatches(b.getBatches());
		i.setSpecialBatchStaffAvailability(b.getSpecialBatchStaffAvailability());
		i.setSeatingStrengthOfflineOnline(b.getSeatingStrengthOfflineOnline());
		i.setOnOffCenterAvailability(b.getOnOffCenterAvailability());
		i.setOnlineExamAvailability(b.getOnlineExamAvailability());
		i.setOfflineExamAvailability(b.getOfflineExamAvailability());
		i.setSeatingStrengthOffline(b.getSeatingStrengthOffline());
		i.setDeleted(b.getDeleted());
		i.setEduSocietyId(b.getEduSocietyId());
		i.setTalukaId(b.getTalukaId());
		i.setStateId(b.getStateId());
		i.setDistrictId(b.getDistrictId());
		i.setDivisionId(b.getDivisionId());
		i.setZoneId(b.getZoneId());
		i.setPowerBackup(b.getPowerBackup());
		i.setDigitalInfrastructureAvailability(b.getDigitalInfrastructureAvailability());
		i.setNonTeachingStaff(b.getNonTeachingStaff());
		i.setStaffAvailabilityPhysics(b.getStaffAvailabilityPhysics());
		i.setStaffAvailabilityChemistry(b.getStaffAvailabilityChemistry());
		i.setStaffAvailabilityBotney(b.getStaffAvailabilityBotney());
		i.setStaffAvailabilityZoology(b.getStaffAvailabilityZoology());
		i.setStaffAvailabilityMath(b.getStaffAvailabilityMath());
		i.setDistinction(b.getDistinction());
		i.setTotalIntake(b.getTotalIntake());
		i.setStatus(b.getStatus());
		i.setTokenId(b.getTokenId());
		i.setDisabled(b.isDisabled());
		i.setInstituteDiscount(b.getInstituteDiscount());
		i.setCenterLevel(b.getCenterLevel());
		i.setCreatedAt(b.getCreatedAt());
		i.setUpdatedAt(b.getUpdatedAt());
		return i;
	}

	@Override
	public InstituteBean searchByKeywords(KeywordSearchRequest request) {
		String indexNumber = request.getKeyword1() + "." + request.getKeyword2() + "." + request.getKeyword3();
		Optional<Institute> optionalInstitute = repository.searchByIndexNumber(indexNumber);
		InstituteBean bean = null;
		if (optionalInstitute.isPresent()) {
			bean = toBean(optionalInstitute.get());
		}
		return bean;
	}

	private InstituteBean toBean(Institute i) {
		InstituteBean b = new InstituteBean();
		b.setIndexNumber(i.getIndexNumber());
		b.setUdiNumber(i.getUdiNumber());
		b.setInstituteName(i.getInstituteName());
		b.setInstituteAddessLine1(i.getInstituteAddessLine1());
		b.setInstituteAddessLine2(i.getInstituteAddessLine2());
		b.setPlace(i.getPlace());
		b.setLatitude(i.getLatitude());
		b.setLongitude(i.getLongitude());
		b.setPinCode(i.getPinCode());
		b.setTelephone(i.getTelephone());
		b.setMobileNumber(i.getMobileNumber());
		b.setEmailId(i.getEmailId());
		b.setIntakeCapacity(i.getIntakeCapacity());
		b.setIntakeCapacity12th(i.getIntakeCapacity12th());
		b.setStaffDetails(i.getStaffDetails());
		b.setDigiInfraAvailability(i.getDigiInfraAvailability());
		b.setBatches(i.getBatches());
		b.setSpecialBatchStaffAvailability(i.getSpecialBatchStaffAvailability());
		b.setSeatingStrengthOfflineOnline(i.getSeatingStrengthOfflineOnline());
		b.setOnOffCenterAvailability(i.getOnOffCenterAvailability());
		b.setOnlineExamAvailability(i.getOnlineExamAvailability());
		b.setOfflineExamAvailability(i.getOfflineExamAvailability());
		b.setSeatingStrengthOffline(i.getSeatingStrengthOffline());
		b.setDeleted(i.getDeleted());
		b.setEduSocietyId(i.getEduSocietyId());
		b.setTalukaId(i.getTalukaId());
		b.setStateId(i.getStateId());
		b.setDistrictId(i.getDistrictId());
		b.setDivisionId(i.getDivisionId());
		b.setZoneId(i.getZoneId());
		b.setPowerBackup(i.getPowerBackup());
		b.setDigitalInfrastructureAvailability(i.getDigitalInfrastructureAvailability());
		b.setNonTeachingStaff(i.getNonTeachingStaff());
		b.setStaffAvailabilityPhysics(i.getStaffAvailabilityPhysics());
		b.setStaffAvailabilityChemistry(i.getStaffAvailabilityChemistry());
		b.setStaffAvailabilityBotney(i.getStaffAvailabilityBotney());
		b.setStaffAvailabilityZoology(i.getStaffAvailabilityZoology());
		b.setStaffAvailabilityMath(i.getStaffAvailabilityMath());
		b.setDistinction(i.getDistinction());
		b.setTotalIntake(i.getTotalIntake());
		b.setStatus(i.getStatus());
		b.setTokenId(i.getTokenId());
		b.setDisabled(i.isDisabled());
		b.setInstituteDiscount(i.getInstituteDiscount());
		b.setCenterLevel(i.getCenterLevel());
		b.setCreatedAt(i.getCreatedAt());
		b.setUpdatedAt(i.getUpdatedAt());
		b.setState(i.getState());
		b.setDistrict(i.getDistrict());
		b.setTaluka(i.getTaluka());
		return b;
	}

}