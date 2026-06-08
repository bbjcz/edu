package com.bbjcz.edu.mapper;

import com.bbjcz.edu.dto.AvailableClassDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnrollmentMapper {
    @Insert("""
            insert into enrollment (student_id, class_id)
            select student_id, #{classId}
            from users where role = 'student' and id = #{uid}
            """)
    int enrollStudentInClass(Integer uid, Integer classId);

    @Delete("""
            delete from enrollment
            where student_id in (
            	select student_id from users
            	where id = #{uid} and role = 'student'
            ) and class_id = #{classId}
            and score is null
            """)
    int unenrollStudentFromClass(Integer uid, Integer classId);

    @Select("""
            select class.id class_id, course.name course_name, teacher.name teacher_name
            from class
            join course on class.course_id = course.id
            join teacher on class.teacher_id = teacher.id
            cross join users
            where users.id = #{uid} and users.role = 'student'
            and class.course_id not in (
                select class.course_id
                from class
                join enrollment on enrollment.class_id = class.id
                where enrollment.student_id = users.student_id
            )
            """)
    List<AvailableClassDTO> getUnenrolledClassesByStudentId(Integer uid);
}
