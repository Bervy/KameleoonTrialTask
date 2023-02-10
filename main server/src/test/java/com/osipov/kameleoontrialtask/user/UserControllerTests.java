package com.osipov.kameleoontrialtask.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osipov.kameleoontrialtask.controller.impl.UserControllerImpl;
import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserControllerImpl.class)
class UserControllerTests {
    private final UserInDto userInDto = UserInDto.builder()
            .name("userName1")
            .password("password")
            .email("userMail1@ya.ru")
            .build();
    private final UserOutDto userOutDto = UserOutDto.builder()
            .id(1L)
            .name("userName1")
            .email("userMail1@ya.ru")
            .build();
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddUser() throws Exception {
        when(userService.create(any())).thenReturn(Optional.ofNullable(userOutDto));

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userInDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(userOutDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userOutDto.getName())))
                .andExpect(jsonPath("$.email", is(userOutDto.getEmail())));
    }
}