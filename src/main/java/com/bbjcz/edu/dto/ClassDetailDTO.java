package com.bbjcz.edu.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClassDetailDTO {
    List<ClassScoreDTO> classScoreList;
    ClassInfoDTO classInfo;
}
