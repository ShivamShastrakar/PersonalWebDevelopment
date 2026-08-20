package com.mahaexam.tenant.management.controller;

import java.util.Objects;

import com.mahaexam.tenant.management.bean.AddressBean;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean;
import com.mahaexam.tenant.management.bean.ChannelPartnerUpdate;
import com.mahaexam.tenant.management.bean.NetworkPartnerUpdate;
import com.mahaexam.tenant.management.model.BankAccount;

import jakarta.validation.Valid;

public class ApplicationUserProfileConverter {

    public static ApplicationUserProfileBean toBean(ChannelPartnerUpdate channelPartnerUpdate, NetworkPartnerUpdate networkPartnerUpdate) {
    	if (channelPartnerUpdate == null && networkPartnerUpdate == null ) return null;
        ApplicationUserProfileBean bean = new ApplicationUserProfileBean();
		if (Objects.nonNull(channelPartnerUpdate)) {
			bean.setUserType(channelPartnerUpdate.getUserType());
			bean.setFirstName(channelPartnerUpdate.getFirstName());
			bean.setLastName(channelPartnerUpdate.getLastName());
			bean.setGender(channelPartnerUpdate.getGender());
			bean.setDateOfBirth(channelPartnerUpdate.getDateOfBirth());
			bean.setAadharNumber(null);
			bean.setRegisteredMobileNumber(channelPartnerUpdate.getRegisteredMobileNumber());
			bean.setWhatsappNumber(channelPartnerUpdate.getWhatsappNumber());
			bean.setEmail(channelPartnerUpdate.getEmail());
			bean.setAdditionalCommissionPercent(channelPartnerUpdate.getAdditionalCommissionPercent());

			// Convert nested objects
			bean.setChannelPartner(toChannelPartnerDTO(channelPartnerUpdate));
		    bean.setAddress(toAddress(channelPartnerUpdate, null));
		    BankAccount bankAccount = new BankAccount();
	        bankAccount.setBankName(channelPartnerUpdate.getBankName()); 
	        bankAccount.setBranchName(channelPartnerUpdate.getBranchName());
	        bankAccount.setAccountNumber(channelPartnerUpdate.getAccountNumber());
	        bankAccount.setIfscCode(channelPartnerUpdate.getIfscCode());
	        bean.setBankAccount(bankAccount);
		}
		if (Objects.nonNull(networkPartnerUpdate)) {
			bean.setUserType(networkPartnerUpdate.getUserType());
			bean.setFirstName(networkPartnerUpdate.getFirstName());
			bean.setLastName(networkPartnerUpdate.getLastName());
			bean.setGender(networkPartnerUpdate.getGender());
			bean.setDateOfBirth(networkPartnerUpdate.getDateOfBirth());
			bean.setAadharNumber(null);
			bean.setRegisteredMobileNumber(networkPartnerUpdate.getRegisteredMobileNumber());
			bean.setWhatsappNumber(networkPartnerUpdate.getWhatsappNumber());
			bean.setEmail(networkPartnerUpdate.getEmail());
			bean.setAdditionalCommissionPercent(networkPartnerUpdate.getAdditionalCommissionPercent());
			bean.setAddress(toAddress(null, networkPartnerUpdate));
			bean.setTeacher(toNetworkPartnerDTO(networkPartnerUpdate));
			
			
		} 
        
      //  bean.setStudent(null);
        return bean;
    }

   private static AddressBean toAddress(ChannelPartnerUpdate channelPartnerUpdate , NetworkPartnerUpdate networkPartnerUpdate ) {
	   
        if (channelPartnerUpdate == null && networkPartnerUpdate == null) return null;
        AddressBean bean = new AddressBean();
        if(Objects.nonNull(networkPartnerUpdate)) {
            bean.setAddressId(networkPartnerUpdate.getAddressId());
            bean.setAddressText(networkPartnerUpdate.getAddressText());
            bean.setUserId(networkPartnerUpdate.getUserId());
            bean.setStateId(networkPartnerUpdate.getStateId());
            bean.setDistrictId(networkPartnerUpdate.getDistrictId());
            bean.setTalukaId(networkPartnerUpdate.getTalukaId());
            bean.setPlace(networkPartnerUpdate.getPlace());
            bean.setPincode(networkPartnerUpdate.getPinCode());
           
        }
        if(Objects.nonNull(channelPartnerUpdate)) {
             bean.setAddressId(channelPartnerUpdate.getAddressId());
             bean.setAddressText(channelPartnerUpdate.getAddressText());
             bean.setUserId(channelPartnerUpdate.getUserId());
             bean.setStateId(channelPartnerUpdate.getStateId());
             bean.setDistrictId(channelPartnerUpdate.getDistrictId());
             bean.setTalukaId(channelPartnerUpdate.getTalukaId());
             bean.setPlace(channelPartnerUpdate.getPlace());
             bean.setPincode(channelPartnerUpdate.getPinCode());
        } 
        return bean;
       
   }

    // ChannelPartner conversion from update entity to DTO
    private static ApplicationUserProfileBean.ChannelPartnerDTO toChannelPartnerDTO(ChannelPartnerUpdate entity) {
        if (entity == null) return null;
        ApplicationUserProfileBean.ChannelPartnerDTO dto = new ApplicationUserProfileBean.ChannelPartnerDTO();
        dto.setChannelPartnerId(entity.getId().longValue());
        dto.setCompanyName(entity.getCompanyName());
        dto.setBusinessType(entity.getBusinessType());
        dto.setPanNumber(entity.getPanNumber());
        dto.setTanNumber(entity.getTanNumber());
        dto.setGstNumber(entity.getGstNumber());
        dto.setBusinessExpYears(entity.getBusinessExpYears());
        dto.setServiceType(entity.getServiceType());
        dto.setDeeperAssociationYears(entity.getDeeperAssociationYears());
//        dto.setParentPartnerId(entity.getParentPartnerId());
        return dto;
    }
    
	private static ApplicationUserProfileBean.TeacherDTO toNetworkPartnerDTO(NetworkPartnerUpdate entity) {
		if (entity == null)
			return null;
		ApplicationUserProfileBean.TeacherDTO teacherDTO = new ApplicationUserProfileBean.TeacherDTO();
		teacherDTO.setTeacherId(entity.getId().longValue());

		teacherDTO.setInstituteIndexNumber(entity.getInstituteIndexNumber());

		teacherDTO.setInService(entity.getInService());

		teacherDTO.setSubjectId(entity.getSubjectId());

		teacherDTO.setTotalExperienceYears(entity.getTotalExperienceYears());

		teacherDTO.setAreaOfInterest(entity.getAreaOfInterest());

		teacherDTO.setOnlineLectureTaken(entity.getOnlineLectureTaken());

		teacherDTO.setQualification(entity.getQualification());

		teacherDTO.setTeachingExperience(entity.getTeachingExperience());

		teacherDTO.setValuationExperience(entity.getValuationExperience());

		teacherDTO.setModerationExperience(entity.getModerationExperience());

		teacherDTO.setChefModerationExperience(entity.getChefModerationExperience());

		teacherDTO.setBoardPaperSettingExperience(entity.getBoardPaperSettingExperience());

		teacherDTO.setMhtCetPaperSettingExperience(entity.getMhtCetPaperSettingExperience());

		teacherDTO.setNeetPaperSettingExperience(entity.getNeetPaperSettingExperience());

		teacherDTO.setJeePaperSettingExperience(entity.getJeePaperSettingExperience());

		teacherDTO.setKvpyPaperSettingExperience(entity.getKvpyPaperSettingExperience());

		teacherDTO.setSpecialtyTopicsSubjects(entity.getSpecialtyTopicsSubjects());

		teacherDTO.setJeeExp(entity.getJeeExp());

		teacherDTO.setMhtCetExp(entity.getMhtCetExp());

		teacherDTO.setNeetExp(entity.getNeetExp());

		teacherDTO.setTotalExp(entity.getTotalExp());

		teacherDTO.setIndividualRefCode(entity.getIndividualRefCode());

		teacherDTO.setRefCode(entity.getRefCode());

		teacherDTO.setAddressText(entity.getAddressText());

		teacherDTO.setStateId(entity.getStateId());

		teacherDTO.setDistrictId(entity.getDistrictId());

		teacherDTO.setTalukaId(entity.getTalukaId());

		teacherDTO.setPlace(entity.getPlace());

		teacherDTO.setPinCode(entity.getPinCode());

//        dto.setParentPartnerId(entity.getParentPartnerId());
		return teacherDTO;
	}

}

