package com.example.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User("john", "password");
        when(userService.login("john", "password")).thenReturn(user);

        mockMvc.perform(post("/login")
                .param("username", "john")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(userService.login("john", "password")).thenReturn(null);

        mockMvc.perform(post("/login")
                .param("username", "john")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("failure"))
                .andExpect(model().attributeDoesNotExist("user"));
    }
}