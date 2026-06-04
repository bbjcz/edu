package com.bbjcz.edu.dto;

import lombok.Data;

@Data
public class StudentScoreDTO {
    private String courseName;
    private String teacherName;
    private Integer point;
    private Integer score;
}
