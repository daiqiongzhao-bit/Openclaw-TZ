package com.hr.controller;

import com.hr.entity.Resume;
import com.hr.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @PostMapping
    public Resume addResume(@RequestBody Resume resume) {
        return resumeService.addResume(resume);
    }

    @PutMapping
    public Resume updateResume(@RequestBody Resume resume) {
        return resumeService.updateResume(resume);
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
    }

    @GetMapping("/{id}")
    public Resume getResumeById(@PathVariable Long id) {
        return resumeService.getResumeById(id);
    }

    @GetMapping("/requirement/{requirementId}")
    public List<Resume> getResumesByRequirementId(@PathVariable Long requirementId) {
        return resumeService.getResumesByRequirementId(requirementId);
    }

    @GetMapping("/status/{status}")
    public List<Resume> getResumesByStatus(@PathVariable Integer status) {
        return resumeService.getResumesByStatus(status);
    }

    @GetMapping("/search")
    public List<Resume> searchResumes(@RequestParam String keyword) {
        return resumeService.searchResumes(keyword);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Resume> getResumesByTenantId(@PathVariable Long tenantId) {
        return resumeService.getResumesByTenantId(tenantId);
    }
}