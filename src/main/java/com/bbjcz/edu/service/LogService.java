package com.bbjcz.edu.service;

import com.bbjcz.edu.entity.ScoreUpdateLog;
import com.bbjcz.edu.mapper.LogMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogMapper logMapper;

    public LogService(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    public List<ScoreUpdateLog> getAllLogs() {
        return logMapper.getAllLogs();
    }
}
