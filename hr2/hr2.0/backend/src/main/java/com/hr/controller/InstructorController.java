package com.hr.controller;

import com.hr.entity.Instructor;
import com.hr.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training/instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public Instructor addInstructor(@RequestBody Instructor instructor) {
        return instructorService.addInstructor(instructor);
    }

    @PutMapping
    public Instructor updateInstructor(@RequestBody Instructor instructor) {
        return instructorService.updateInstructor(instructor);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
    }

    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Instructor> getInstructorsByTenantId(@PathVariable Long tenantId) {
        return instructorService.getInstructorsByTenantId(tenantId);
    }

    @GetMapping("/expertise/{expertise}")
    public List<Instructor> getInstructorsByExpertise(@PathVariable String expertise) {
        return instructorService.getInstructorsByExpertise(expertise);
    }

    @GetMapping("/active/{tenantId}")
    public List<Instructor> getActiveInstructors(@PathVariable Long tenantId) {
        return instructorService.getActiveInstructors(tenantId);
    }

    @GetMapping("/search")
    public List<Instructor> searchInstructors(@RequestParam String keyword) {
        return instructorService.searchInstructors(keyword);
    }
}