package com.bbjcz.edu.mapper;

import com.bbjcz.edu.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select id, name, point from course")
    List<Course> getAllCourses();

    @Select("""
            insert into course (name, point)
            values (#{name}, #{point})
            returning id
            """)
    Integer insertCourse(Course course);

    @Update("""
            update course
            set name = #{course.name}, point = #{course.point}, id = #{course.id}
            where id = #{old_id}
            """)
    int updateCourse(Integer old_id, Course course);

    @Delete("""
            delete from course
            where id = #{id}
            """)
    int deleteCourse(Integer id);
}
