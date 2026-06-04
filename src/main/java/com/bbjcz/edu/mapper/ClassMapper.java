package com.bbjcz.edu.mapper;

import com.bbjcz.edu.dto.ClassDTO;
import com.bbjcz.edu.dto.ClassInfoDTO;
import com.bbjcz.edu.dto.ClassScoreDTO;
import com.bbjcz.edu.entity.EduClass;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Select("select id, course_id, teacher_id from class")
    List<EduClass> getAllClasses();

    @Select("""
            insert into class (course_id, teacher_id)
            values (#{courseId}, #{teacherId})
            returning id
            """)
    Integer insertClass(EduClass eduClass);

    @Update("""
            update class
            set course_id = #{eduClass.courseId}, teacher_id = #{eduClass.teacherId}, id = #{eduClass.id}
            where id = #{old_id}
            """)
    int updateClass(Integer old_id, EduClass eduClass);

    @Delete("""
            delete from class
            where id = #{id}
            """)
    int deleteClass(Integer id);

    @Select("""
            select class.id class_id, course.name course_name
            from users
            join class on users.teacher_id = class.teacher_id
            join course on class.course_id = course.id
            where users.role = 'teacher' and users.id = #{uid}
            """)
    List<ClassDTO> getClassByTeacherId(Integer uid);

    @Select("""
            select enrollment.student_id, student.name student_name, enrollment.score
            from users
            join class on users.teacher_id = class.teacher_id
            join enrollment on enrollment.class_id = class.id
            join student on enrollment.student_id = student.id
            where users.role = 'teacher' and users.id = #{uid} and class.id = #{classId}
            """)
    List<ClassScoreDTO> getClassScoreByClassId(Integer uid, Integer classId);

    @Select("""
            select course.name course_name, round(avg(enrollment.score), 1)::float8 average_score
            from users
            join class on users.teacher_id = class.teacher_id
            join course on class.course_id = course.id
            left join enrollment on enrollment.class_id = class.id
            where users.role = 'teacher' and users.id = #{uid} and class.id = #{classId}
            group by course.name
            """)
    ClassInfoDTO getClassInfoByClassId(Integer uid, Integer classId);

    @Update("""
            update enrollment
            set score = #{score}
            where class_id = #{classId} and student_id = #{studentId}
            and class_id in (
            	select class_id
            	from class join users
            	on class.teacher_id = users.teacher_id
            	where users.role = 'teacher' and users.id = #{uid}
            )
            """)
    int updateStudentScore(Integer uid, Integer classId, Integer studentId, Integer score);

}
