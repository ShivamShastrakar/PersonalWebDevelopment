package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.bean.InstituteBean;
import com.mahaexam.common.bean.KeywordSearchRequest;
import com.mahaexam.common.model.Institute;

public interface InstituteService {
    int createInstitute(InstituteBean bean);
    Institute getInstituteById(int id);
    List<Institute> getAllInstitutes();
    int updateInstitute(int id, InstituteBean bean);
    int deleteInstitute(int id);
    InstituteBean searchByKeywords(KeywordSearchRequest request);
	
}