package com.bbjcz.edu.controller;

import com.bbjcz.edu.dto.MeDTO;
import com.bbjcz.edu.dto.StudentScoreAllDTO;
import com.bbjcz.edu.service.MeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {
    private final MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }

    @GetMapping
    public MeDTO getMe(HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return null;
        }
        return meService.getMe(id);
    }

    @GetMapping("/score")
    public StudentScoreAllDTO getStudentScore(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("id");
        if (uid == null) {
            return null;
        }
        return meService.getStudentScore(uid);
    }
}
