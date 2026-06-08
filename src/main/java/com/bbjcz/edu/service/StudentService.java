package com.bbjcz.edu.service;

import com.bbjcz.edu.dto.CreatedStudentDTO;
import com.bbjcz.edu.entity.Student;
import com.bbjcz.edu.entity.User;
import com.bbjcz.edu.mapper.StudentMapper;
import com.bbjcz.edu.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;

    public StudentService(StudentMapper studentMapper, UserMapper userMapper) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
    }

    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @Transactional
    public CreatedStudentDTO insertStudent(Student student) {
        Integer studentId = studentMapper.insertStudent(student);
        User user = new User();
        user.setRole("student");
        user.setStudentId(studentId);
        Integer userId = userMapper.insertUser(user);
        return new CreatedStudentDTO(userId, studentId);
    }

    public boolean updateStudent(Integer old_id, Student student) {
        return studentMapper.updateStudent(old_id, student) > 0;
    }

    public boolean deleteStudent(Integer id) {
        return studentMapper.deleteStudent(id) > 0;
    }
}
