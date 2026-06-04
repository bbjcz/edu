package com.bbjcz.edu.controller;

import com.bbjcz.edu.entity.Course;
import com.bbjcz.edu.service.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public List<Course> getAllCourses(HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return courseService.getAllCourses();
    }

    @PostMapping
    public Integer insertCourse(@RequestBody Course course, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return courseService.insertCourse(course);
    }

    @PutMapping("/{old_id}")
    public boolean updateCourse(@PathVariable Integer old_id, @RequestBody Course course, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return courseService.updateCourse(old_id, course);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCourse(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return courseService.deleteCourse(id);
    }
}
