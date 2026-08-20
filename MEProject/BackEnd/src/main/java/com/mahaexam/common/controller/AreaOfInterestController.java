package com.mahaexam.common.controller;

import com.mahaexam.common.model.AreaOfInterest;
import com.mahaexam.common.service.AreaOfInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/areas-of-interest")
public class AreaOfInterestController {

    private final AreaOfInterestService service;

    @Autowired
    public AreaOfInterestController(AreaOfInterestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AreaOfInterest> create(@RequestBody AreaOfInterest areaOfInterest) {
        AreaOfInterest created = service.create(areaOfInterest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaOfInterest> getById(@PathVariable Integer id) {
        Optional<AreaOfInterest> area = service.getById(id);
        return area.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AreaOfInterest>> getAll() {
        List<AreaOfInterest> areas = service.getAll();
        return ResponseEntity.ok(areas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaOfInterest> update(@PathVariable Integer id, @RequestBody AreaOfInterest areaOfInterest) {
        AreaOfInterest updated = service.update(id, areaOfInterest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
