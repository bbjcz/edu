package com.bbjcz.edu.service;

import com.bbjcz.edu.dto.MeDTO;
import com.bbjcz.edu.dto.StudentScoreAllDTO;
import com.bbjcz.edu.dto.StudentScoreDTO;
import com.bbjcz.edu.mapper.MeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeService {
    private final MeMapper meMapper;

    public MeService(MeMapper meMapper) {
        this.meMapper = meMapper;
    }

    public MeDTO getMe(Integer id) {
        return meMapper.getMeById(id);
    }

    public StudentScoreAllDTO getStudentScore(Integer uid) {
        List<StudentScoreDTO> studentScoreList = meMapper.getStudentScoreById(uid);
        Double averageScore = meMapper.getStudentAverageScoreById(uid);
        StudentScoreAllDTO studentScoreAllDTO = new StudentScoreAllDTO();
        studentScoreAllDTO.setStudentScoreList(studentScoreList);
        studentScoreAllDTO.setAverageScore(averageScore);
        return studentScoreAllDTO;
    }
}
