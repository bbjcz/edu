package com.bbjcz.edu.mapper;

import com.bbjcz.edu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select password from users where id = #{id}")
    String getPasswordById(Integer id);

    @Select("""
            select exists (
            	select id from users
            	where id = #{uid} and role = 'admin'
            )
            """)
    boolean isAdmin(Integer uid);

    @Select("select id, role, student_id, teacher_id from users")
    List<User> getAllUsers();

    @Select("""
            insert into users (password, role, student_id, teacher_id)
            values ('password', #{role}, #{studentId}, #{teacherId})
            returning id
            """)
    Integer insertUser(User user);

    @Update("""
            update users
            set password = #{newPassword}
            where id = #{id} and password = #{oldPassword}
            """)
    int updatePassword(Integer id, String oldPassword, String newPassword);

    @Delete("delete from users where id = #{id} and role <> 'admin'")
    int deleteNonAdminUser(Integer id);
}
