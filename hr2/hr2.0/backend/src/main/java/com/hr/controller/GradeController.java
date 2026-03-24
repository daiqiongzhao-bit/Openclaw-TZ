package com.hr.controller;

import com.hr.entity.Grade;
import com.hr.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/tenant/{tenantId}")
    public List<Grade> getGradesByTenantId(@PathVariable Long tenantId) {
        return gradeService.getGradesByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public Grade getGradeById(@PathVariable Long id) {
        return gradeService.getGradeById(id);
    }

    @PostMapping
    public boolean addGrade(@RequestBody Grade grade) {
        return gradeService.addGrade(grade);
    }

    @PutMapping
    public boolean updateGrade(@RequestBody Grade grade) {
        return gradeService.updateGrade(grade);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGrade(@PathVariable Long id) {
        return gradeService.deleteGrade(id);
    }
}