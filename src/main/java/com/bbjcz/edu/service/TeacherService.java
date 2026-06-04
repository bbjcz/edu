package com.bbjcz.edu.service;

import com.bbjcz.edu.entity.Teacher;
import com.bbjcz.edu.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public List<Teacher> getAllTeachers() {
        return teacherMapper.getAllTeachers();
    }

    public Integer insertTeacher(Teacher teacher) {
        return teacherMapper.insertTeacher(teacher);
    }

    public boolean updateTeacher(Integer old_id, Teacher teacher) {
        return teacherMapper.updateTeacher(old_id, teacher) > 0;
    }

    public boolean deleteTeacher(Integer id) {
        return teacherMapper.deleteTeacher(id) > 0;
    }
}
