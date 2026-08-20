package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.bean.EducationSocietyBean;
import com.mahaexam.common.model.EducationSociety;

public interface EducationSocietyService {
    int createSociety(EducationSocietyBean bean);
    EducationSociety getSocietyById(int id);
    List<EducationSociety> getAllSocieties();
    int updateSociety(int id, EducationSocietyBean bean);
    void delete(Integer id);
}