package com.bbjcz.edu.mapper;

import com.bbjcz.edu.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("select id, name from teacher")
    List<Teacher> getAllTeachers();

    @Select("""
            insert into teacher (name)
            values (#{name})
            returning id
            """)
    Integer insertTeacher(Teacher teacher);

    @Update("""
            update teacher
            set name = #{teacher.name}, id = #{teacher.id}
            where id = #{old_id}
            """)
    int updateTeacher(Integer old_id, Teacher teacher);

    @Delete("""
            delete from teacher
            where id = #{id}
            """)
    int deleteTeacher(Integer id);
}
