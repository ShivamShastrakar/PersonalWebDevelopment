package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.tenant.management.model.AcademicYear;
import com.mahaexam.tenant.management.service.AcademicYearService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academic-years")
public class AcademicYearController extends BaseController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }


    @PostMapping
    public ResponseEntity<AcademicYear> create(@RequestBody AcademicYear ay) {
        ay.setTenantId(getUser().getTenantId());
        return ResponseEntity.ok(academicYearService.save(ay));
    }

    @GetMapping
    public ResponseEntity<List<AcademicYear>> findAll() {
        return ResponseEntity.ok(academicYearService.findAllByTenantId(getUser().getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYear> update(@PathVariable Long id, @RequestBody AcademicYear ay) {
        ay.setId(id);
        ay.setTenantId(getUser().getTenantId());
        return ResponseEntity.ok(academicYearService.update(ay));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        academicYearService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
