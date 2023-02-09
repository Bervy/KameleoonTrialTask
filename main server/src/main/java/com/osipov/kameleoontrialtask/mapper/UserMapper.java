package com.osipov.kameleoontrialtask.mapper;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.model.User;

public class UserMapper {
    public static UserOutDto userToDto(User user) {
        return UserOutDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User dtoToUser(UserInDto userInDto) {
        return User.builder()
                .name(userInDto.getName())
                .email(userInDto.getEmail())
                .build();
    }
}