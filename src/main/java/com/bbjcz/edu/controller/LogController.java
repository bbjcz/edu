package com.bbjcz.edu.controller;

import com.bbjcz.edu.entity.ScoreUpdateLog;
import com.bbjcz.edu.service.LogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/score")
    public List<ScoreUpdateLog> getAllLogs(HttpSession session) {
        if (session.getAttribute("id") == null) {
            return null;
        }
        return logService.getAllLogs();
    }
}
