package com.henrique.chat_api.user_account.unit;

import com.henrique.chat_api.configurations.UserDetailsConfig;
import com.henrique.chat_api.controllers.UserController;
import com.henrique.chat_api.services.JwtService;
import com.henrique.chat_api.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserDetailsConfig userDetailsConfig;

    @Test
    public void shouldThrowMethodNotValidException() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                ).andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }
}
