package com.bbjcz.edu.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentScoreAllDTO {
    private List<StudentScoreDTO> studentScoreList;
    private Double averageScore;
}
