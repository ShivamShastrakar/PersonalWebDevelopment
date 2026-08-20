package com.mahaexam.common.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahaexam.common.bean.EducationSocietyBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.EducationSociety;
import com.mahaexam.common.repo.EducationSocietyRepository;

@Service
public class EducationSocietyServiceImpl implements EducationSocietyService {

    @Autowired
    private EducationSocietyRepository repository;

    @Override
    public int createSociety(EducationSocietyBean bean) {
    	if (repository.existsBySocietyName(bean.getSocietyName())) {
            throw new ValidationException("Society name already exists.");
        }
        EducationSociety s = toEntity(bean);
        s.setCreatedAt(LocalDateTime.now());
        return repository.save(s);
    }

    @Override
    public EducationSociety getSocietyById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<EducationSociety> getAllSocieties() {
        return repository.findAll();
    }

    @Override
    public int updateSociety(int id, EducationSocietyBean bean) {
    	if (repository.existsBySocietyNameExceptId(bean.getSocietyName(), id)) {
            throw new ValidationException("Society name already exists.");
        }
        EducationSociety s = toEntity(bean);
        s.setId(id);
        s.setUpdatedAt(LocalDateTime.now());
        return repository.update(s);
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Education society ID is required");
        }
        repository.delete(id);
    }

    private EducationSociety toEntity(EducationSocietyBean b) {
        EducationSociety s = new EducationSociety();
        s.setSocietyName(b.getSocietyName());
        s.setDeleted(b.getDeleted() != null ? b.getDeleted() : "0");  // Default to "0"
        s.setDisabled(b.isDisabled());
        s.setCreatedAt(b.getCreatedAt());
        s.setUpdatedAt(b.getUpdatedAt());
        s.setDeletedAt(b.getDeletedAt());
        return s;
    }
}