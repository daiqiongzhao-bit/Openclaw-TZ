package com.hr.controller;

import com.hr.entity.Course;
import com.hr.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping
    public Course updateCourse(@RequestBody Course course) {
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Course> getCoursesByTenantId(@PathVariable Long tenantId) {
        return courseService.getCoursesByTenantId(tenantId);
    }

    @GetMapping("/category/{category}")
    public List<Course> getCoursesByCategory(@PathVariable String category) {
        return courseService.getCoursesByCategory(category);
    }

    @GetMapping("/level/{level}")
    public List<Course> getCoursesByLevel(@PathVariable String level) {
        return courseService.getCoursesByLevel(level);
    }

    @GetMapping("/type/{type}")
    public List<Course> getCoursesByType(@PathVariable String type) {
        return courseService.getCoursesByType(type);
    }

    @GetMapping("/active/{tenantId}")
    public List<Course> getActiveCourses(@PathVariable Long tenantId) {
        return courseService.getActiveCourses(tenantId);
    }

    @GetMapping("/search")
    public List<Course> searchCourses(@RequestParam String keyword) {
        return courseService.searchCourses(keyword);
    }
}