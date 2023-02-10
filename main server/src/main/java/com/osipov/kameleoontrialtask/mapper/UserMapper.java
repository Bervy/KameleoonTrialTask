package com.osipov.kameleoontrialtask.mapper;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.model.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public UserOutDto userToDto(User user) {
        return UserOutDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User dtoToUser(UserInDto userInDto) {
        return User.builder()
                .name(userInDto.getName())
                .email(userInDto.getEmail())
                .password(userInDto.getPassword())
                .dateOfCreation(LocalDateTime.now())
                .build();
    }
}