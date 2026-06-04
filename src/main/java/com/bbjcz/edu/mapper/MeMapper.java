package com.bbjcz.edu.mapper;

import com.bbjcz.edu.dto.MeDTO;
import com.bbjcz.edu.dto.StudentScoreDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MeMapper {
    @Select("""
            select users.id uid,
                   coalesce(student.id, teacher.id) id,
                   coalesce(student.name, teacher.name) name,
                   users.role
            from users
            left join student on users.student_id = student.id
            left join teacher on users.teacher_id = teacher.id
            where users.id = #{uid}
            """)
    MeDTO getMeById(Integer uid);

    @Select("""
            select course.name course_name, teacher.name teacher_name, course.point, enrollment.score
            from users
            join enrollment on users.student_id = enrollment.student_id
            join class on enrollment.class_id = class.id
            join course on class.course_id = course.id
            join teacher on class.teacher_id = teacher.id
            where users.role = 'student' and users.id = #{uid}
            """)
    List<StudentScoreDTO> getStudentScoreById(Integer uid);

    @Select("""
            select round(
                sum(enrollment.score * course.point)::numeric /
                nullif(sum(course.point) filter (where enrollment.score is not null), 0),
                1
            )::float8 average_score
            from users
            join enrollment on users.student_id = enrollment.student_id
            join class on enrollment.class_id = class.id
            join course on class.course_id = course.id
            where users.role = 'student' and users.id = #{uid}
            group by users.id
            """)
    Double getStudentAverageScoreById(Integer uid);
}
