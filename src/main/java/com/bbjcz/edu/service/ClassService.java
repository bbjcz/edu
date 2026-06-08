package com.bbjcz.edu.service;

import com.bbjcz.edu.dto.ClassAdminDTO;
import com.bbjcz.edu.dto.ClassDTO;
import com.bbjcz.edu.dto.ClassDetailDTO;
import com.bbjcz.edu.entity.EduClass;
import com.bbjcz.edu.mapper.ClassMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    private final ClassMapper classMapper;

    public ClassService(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    public List<ClassAdminDTO> getAllClasses() {
        return classMapper.getAllClasses();
    }

    public Integer insertClass(EduClass eduClass) {
        return classMapper.insertClass(eduClass);
    }

    public boolean updateClass(Integer old_id, EduClass eduClass) {
        return classMapper.updateClass(old_id, eduClass) > 0;
    }

    public boolean deleteClass(Integer id) {
        return classMapper.deleteClass(id) > 0;
    }

    public List<ClassDTO> getClassByTeacherId(Integer uid) {
        return classMapper.getClassByTeacherId(uid);
    }

    public ClassDetailDTO getClassDetailByClassId(Integer uid, Integer classId) {
        ClassDetailDTO classDetailDTO = new ClassDetailDTO();
        classDetailDTO.setClassInfo(classMapper.getClassInfoByClassId(uid, classId));
        classDetailDTO.setClassScoreList(classMapper.getClassScoreByClassId(uid, classId));
        return classDetailDTO;
    }

    public boolean updateStudentScore(Integer uid, Integer classId, Integer studentId, Integer score) {
        int result = classMapper.updateStudentScore(uid, classId, studentId, score);
        return result > 0;
    }
}
