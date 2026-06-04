package com.bbjcz.edu.mapper;

import com.bbjcz.edu.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select id, name from student")
    List<Student> getAllStudents();

    @Select("""
            insert into student (name)
            values (#{name})
            returning id
            """)
    Integer insertStudent(Student student);

    @Update("""
            update student
            set name = #{student.name}, id = #{student.id}
            where id = #{old_id}
            """)
    int updateStudent(Integer old_id, Student student);

    @Delete("""
            delete from student
            where id = #{id}
            """)
    int deleteStudent(Integer id);
}
