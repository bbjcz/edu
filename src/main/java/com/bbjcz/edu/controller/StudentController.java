package com.bbjcz.edu.controller;

import com.bbjcz.edu.entity.Student;
import com.bbjcz.edu.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<Student> getAllStudents(HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return studentService.getAllStudents();
    }

    @PostMapping
    public Integer insertStudent(@RequestBody Student student, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return studentService.insertStudent(student);
    }

    @PutMapping("/{old_id}")
    public boolean updateStudent(@PathVariable Integer old_id, @RequestBody Student student, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return studentService.updateStudent(old_id, student);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return studentService.deleteStudent(id);
    }

}
