package com.osipov.kameleoontrialtask.service;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.error.ConflictException;
import com.osipov.kameleoontrialtask.mapper.UserMapper;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserOutDto create(UserInDto userInDto) {
        try {
            return UserMapper.userToDto(userRepository.save(UserMapper.dtoToUser(userInDto)));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(USER_ALREADY_EXISTS.getTitle());
        }
    }
}