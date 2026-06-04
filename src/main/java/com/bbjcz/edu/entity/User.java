package com.bbjcz.edu.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String role;
    private Integer studentId;
    private Integer teacherId;
}
