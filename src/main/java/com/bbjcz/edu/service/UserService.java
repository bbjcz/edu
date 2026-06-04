package com.bbjcz.edu.service;

import com.bbjcz.edu.dto.LoginDTO;
import com.bbjcz.edu.entity.User;
import com.bbjcz.edu.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean login(LoginDTO loginDTO) {
        String password = userMapper.getPasswordById(loginDTO.getId());
        boolean isValid = false;
        if (password != null) {
            isValid = password.equals(loginDTO.getPassword());
        }
        return isValid;
    }

//    public MeDTO getMe(Integer id) {
//        return userMapper.getMeById(id);
//    }
    public boolean isAdmin(Integer uid) {
        return userMapper.isAdmin(uid);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    public boolean updatePassword(Integer id, String oldPassword, String newPassword) {
        if (id == null || oldPassword == null || newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        return userMapper.updatePassword(id, oldPassword, newPassword) > 0;
    }

    public boolean deleteNonAdminUser(Integer id) {
        return userMapper.deleteNonAdminUser(id) > 0;
    }
}
