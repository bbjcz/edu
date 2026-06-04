package com.bbjcz.edu.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScoreUpdateLog {
    private Integer id;
    private Integer studentId;
    private Integer classId;
    private Integer newScore;
    private LocalDateTime updateTime;
}
