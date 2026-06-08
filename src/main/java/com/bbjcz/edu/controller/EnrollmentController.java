package com.bbjcz.edu.controller;

import com.bbjcz.edu.dto.AvailableClassDTO;
import com.bbjcz.edu.service.EnrollmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public boolean enrollInClass(@RequestBody Integer classId, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return false;
        }
        return enrollmentService.enrollInClass(uid, classId);
    }

    @DeleteMapping("/{classId}")
    public boolean unenrollFromClass(@PathVariable Integer classId, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return false;
        }
        return enrollmentService.unenrollFromClass(uid, classId);
    }

    @GetMapping("/available")
    public List<AvailableClassDTO> getAvailableClassIdsForStudent(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return null;
        }
        return enrollmentService.getAvailableClassIdsForStudent(uid);
    }
}
