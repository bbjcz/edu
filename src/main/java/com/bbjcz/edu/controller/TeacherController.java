package com.bbjcz.edu.controller;

import com.bbjcz.edu.entity.Teacher;
import com.bbjcz.edu.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public List<Teacher> getAllTeachers(HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return teacherService.getAllTeachers();
    }

    @PostMapping
    public Integer insertTeacher(@RequestBody Teacher teacher, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return teacherService.insertTeacher(teacher);
    }

    @PutMapping("/{old_id}")
    public boolean updateTeacher(@PathVariable Integer old_id, @RequestBody Teacher teacher, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return teacherService.updateTeacher(old_id, teacher);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTeacher(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return teacherService.deleteTeacher(id);
    }
}
