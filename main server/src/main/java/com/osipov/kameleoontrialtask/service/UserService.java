package com.osipov.kameleoontrialtask.service;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;

import java.util.Optional;

public interface UserService {

    Optional<UserOutDto> create(UserInDto userInDto);
}