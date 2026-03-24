package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.CourseDao;
import com.hr.entity.Course;
import com.hr.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {
    @Override
    public Course addCourse(Course course) {
        course.setStatus("draft");
        save(course);
        return course;
    }

    @Override
    public Course updateCourse(Course course) {
        updateById(course);
        return course;
    }

    @Override
    public void deleteCourse(Long id) {
        removeById(id);
    }

    @Override
    public Course getCourseById(Long id) {
        return getById(id);
    }

    @Override
    public List<Course> getCoursesByTenantId(Long tenantId) {
        return lambdaQuery().eq(Course::getTenantId, tenantId).list();
    }

    @Override
    public List<Course> getCoursesByCategory(String category) {
        return lambdaQuery().eq(Course::getCategory, category).list();
    }

    @Override
    public List<Course> getCoursesByLevel(String level) {
        return lambdaQuery().eq(Course::getLevel, level).list();
    }

    @Override
    public List<Course> getCoursesByType(String type) {
        return lambdaQuery().eq(Course::getType, type).list();
    }

    @Override
    public List<Course> getActiveCourses(Long tenantId) {
        return lambdaQuery().eq(Course::getTenantId, tenantId).eq(Course::getStatus, "active").list();
    }

    @Override
    public List<Course> searchCourses(String keyword) {
        return lambdaQuery()
                .like(Course::getCourseName, keyword)
                .or()
                .like(Course::getCourseCode, keyword)
                .or()
                .like(Course::getDescription, keyword)
                .list();
    }
}