package com.bbjcz.edu.dto;

import lombok.Data;

@Data
public class ClassAdminDTO {
    private Integer id;
    private Integer courseId;
    private String courseName;
    private Integer teacherId;
    private String teacherName;
}
