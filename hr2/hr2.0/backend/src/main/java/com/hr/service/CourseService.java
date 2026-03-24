package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    Course addCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourse(Long id);
    Course getCourseById(Long id);
    List<Course> getCoursesByTenantId(Long tenantId);
    List<Course> getCoursesByCategory(String category);
    List<Course> getCoursesByLevel(String level);
    List<Course> getCoursesByType(String type);
    List<Course> getActiveCourses(Long tenantId);
    List<Course> searchCourses(String keyword);
}