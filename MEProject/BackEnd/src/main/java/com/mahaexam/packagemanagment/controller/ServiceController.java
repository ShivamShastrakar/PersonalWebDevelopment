package com.mahaexam.packagemanagment.controller;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.controller.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.ServiceBean;
import com.mahaexam.packagemanagment.service.ServiceService;

@RestController
@RequestMapping("/api/services")
public class ServiceController extends BaseController {

    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponseBean> createService(@RequestBody ServiceBean service) {
        try {
            ServiceBean created = serviceService.createService(service);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Service created successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceBean> getServiceById(Integer id) {
        try {
            Optional<ServiceBean> service = serviceService.getServiceById(id);
            return service.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceBean>> getAllServices() {
        try {
            UserBean user = getUser();
            List<ServiceBean> services = serviceService.getAllServices(user);
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> updateService(@PathVariable Integer id, @RequestBody ServiceBean service) {
        try {
            ServiceBean updated = serviceService.updateService(id, service);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Service updated successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        try {
            serviceService.deleteService(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}