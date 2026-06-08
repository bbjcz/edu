package com.bbjcz.edu.service;

import com.bbjcz.edu.dto.CreatedTeacherDTO;
import com.bbjcz.edu.entity.User;
import com.bbjcz.edu.entity.Teacher;
import com.bbjcz.edu.mapper.TeacherMapper;
import com.bbjcz.edu.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;

    public TeacherService(TeacherMapper teacherMapper, UserMapper userMapper) {
        this.teacherMapper = teacherMapper;
        this.userMapper = userMapper;
    }

    public List<Teacher> getAllTeachers() {
        return teacherMapper.getAllTeachers();
    }

    @Transactional
    public CreatedTeacherDTO insertTeacher(Teacher teacher) {
        Integer teacherId = teacherMapper.insertTeacher(teacher);
        User user = new User();
        user.setRole("teacher");
        user.setTeacherId(teacherId);
        Integer userId = userMapper.insertUser(user);
        return new CreatedTeacherDTO(userId, teacherId);
    }

    public boolean updateTeacher(Integer old_id, Teacher teacher) {
        return teacherMapper.updateTeacher(old_id, teacher) > 0;
    }

    public boolean deleteTeacher(Integer id) {
        return teacherMapper.deleteTeacher(id) > 0;
    }
}
