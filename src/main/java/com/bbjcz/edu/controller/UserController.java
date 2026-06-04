package com.bbjcz.edu.controller;

import com.bbjcz.edu.dto.ChangePasswordDTO;
import com.bbjcz.edu.dto.LoginDTO;
import com.bbjcz.edu.entity.User;
import com.bbjcz.edu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        boolean isValid = userService.login(loginDTO);
        if (isValid) {
            session.setAttribute("id", loginDTO.getId());
            boolean isAdmin = userService.isAdmin(loginDTO.getId());
            session.setAttribute("isAdmin", isAdmin);
        }
        return isValid;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/all")
    public List<User> getAllUsers(HttpSession session) {
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return null;
        }
        return userService.getAllUsers();
    }

    @PostMapping
    public Integer insertUser(@RequestBody User user, HttpSession session) {
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            return null;
        }
        return userService.insertUser(user);
    }

    @PutMapping("/password")
    public boolean updatePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpSession session) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return false;
        }
        return userService.updatePassword(id, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
    }

    @DeleteMapping("/{id}")
    public boolean deleteNonAdminUser(@PathVariable Integer id, HttpSession session) {
        if (session.getAttribute("isAdmin") == null
                || !(boolean) session.getAttribute("isAdmin")) {
            return false;
        }
        return userService.deleteNonAdminUser(id);
    }
}
