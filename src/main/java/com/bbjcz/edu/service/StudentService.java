package com.bbjcz.edu.service;

import com.bbjcz.edu.entity.Student;
import com.bbjcz.edu.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    public Integer insertStudent(Student student) {
        return studentMapper.insertStudent(student);
    }

    public boolean updateStudent(Integer old_id, Student student) {
        return studentMapper.updateStudent(old_id, student) > 0;
    }

    public boolean deleteStudent(Integer id) {
        return studentMapper.deleteStudent(id) > 0;
    }
}
