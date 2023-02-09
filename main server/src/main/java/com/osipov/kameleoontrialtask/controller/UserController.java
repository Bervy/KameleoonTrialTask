package com.osipov.kameleoontrialtask.controller;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserOutDto create(@Valid @RequestBody UserInDto userInDto) {
        return userService.create(userInDto);
    }
}