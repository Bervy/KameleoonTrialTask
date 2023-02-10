package com.osipov.kameleoontrialtask.controller;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;

import java.util.Optional;

public interface UserController {
    Optional<UserOutDto> create(UserInDto userInDto);
}