package com.bbjcz.edu.controller;

import com.bbjcz.edu.dto.ClassAdminDTO;
import com.bbjcz.edu.dto.ClassDTO;
import com.bbjcz.edu.dto.ClassDetailDTO;
import com.bbjcz.edu.entity.EduClass;
import com.bbjcz.edu.service.ClassService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/all")
    public List<ClassAdminDTO> getAllClasses(HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return classService.getAllClasses();
    }

    @PostMapping
    public Integer insertClass(@RequestBody EduClass eduClass, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return null;
        }
        return classService.insertClass(eduClass);
    }

    @PutMapping("/{old_id}")
    public boolean updateClass(@PathVariable Integer old_id, @RequestBody EduClass eduClass, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return classService.updateClass(old_id, eduClass);
    }

    @DeleteMapping("/{id}")
    public boolean deleteClass(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return classService.deleteClass(id);
    }

    @GetMapping
    public List<ClassDTO> getClassByTeacherId(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return null;
        }
        return classService.getClassByTeacherId(uid);
    }

    @GetMapping("/{classId}")
    public ClassDetailDTO getClassDetailByClassId(@PathVariable Integer classId, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return null;
        }
        return classService.getClassDetailByClassId(uid, classId);
    }

    @PutMapping("/{classId}/student/{studentId}/score")
    public boolean updateStudentScore(@PathVariable Integer classId,
                                      @PathVariable Integer studentId,
                                      @RequestBody(required = false) Integer score,
                                      HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return false;
        }
        return classService.updateStudentScore(uid, classId, studentId, score);
    }
}
