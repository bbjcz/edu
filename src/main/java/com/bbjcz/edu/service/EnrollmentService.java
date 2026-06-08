package com.bbjcz.edu.service;

import com.bbjcz.edu.dto.AvailableClassDTO;
import com.bbjcz.edu.mapper.EnrollmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentService(EnrollmentMapper enrollmentMapper) {
        this.enrollmentMapper = enrollmentMapper;
    }

    public boolean enrollInClass(Integer uid, Integer classId) {
        int result = enrollmentMapper.enrollStudentInClass(uid, classId);
        return result > 0;
    }

    public boolean unenrollFromClass(Integer uid, Integer classId) {
        int result = enrollmentMapper.unenrollStudentFromClass(uid, classId);
        return result > 0;
    }

    public List<AvailableClassDTO> getAvailableClassIdsForStudent(Integer uid) {
        return enrollmentMapper.getUnenrolledClassesByStudentId(uid);
    }
}
