package com.bbjcz.edu.controller;

import com.bbjcz.edu.dto.ChangePasswordDTO;
import com.bbjcz.edu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Test
    void updatePasswordUsesCurrentSessionUser() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 3);

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setOldPassword("old");
        changePasswordDTO.setNewPassword("new");
        when(userService.updatePassword(3, "old", "new")).thenReturn(true);

        assertTrue(userController.updatePassword(changePasswordDTO, session));
        verify(userService).updatePassword(3, "old", "new");
    }

    @Test
    void updatePasswordReturnsFalseWhenNotLoggedIn() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        assertFalse(userController.updatePassword(new ChangePasswordDTO(), new MockHttpSession()));
        verify(userService, never()).updatePassword(null, null, null);
    }

    @Test
    void adminCanDeleteNonAdminUser() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("isAdmin", true);
        when(userService.deleteNonAdminUser(5)).thenReturn(true);

        assertTrue(userController.deleteNonAdminUser(5, session));
        verify(userService).deleteNonAdminUser(5);
    }

    @Test
    void nonAdminCannotDeleteUser() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("isAdmin", false);

        assertFalse(userController.deleteNonAdminUser(5, session));
        verify(userService, never()).deleteNonAdminUser(5);
    }
}
