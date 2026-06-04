package com.bbjcz.edu.service;

import com.bbjcz.edu.entity.Course;
import com.bbjcz.edu.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseMapper courseMapper;

    public CourseService(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public List<Course> getAllCourses() {
        return courseMapper.getAllCourses();
    }

    public Integer insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    public boolean updateCourse(Integer old_id, Course course) {
        return courseMapper.updateCourse(old_id, course) > 0;
    }

    public boolean deleteCourse(Integer id) {
        return courseMapper.deleteCourse(id) > 0;
    }
}
