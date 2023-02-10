package com.osipov.kameleoontrialtask.service.impl;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.error.ConflictException;
import com.osipov.kameleoontrialtask.mapper.UserMapper;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import com.osipov.kameleoontrialtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserOutDto> create(UserInDto userInDto) {
        try {
            return Optional.ofNullable(userMapper.userToDto(userRepository.save(userMapper.dtoToUser(userInDto))));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(USER_ALREADY_EXISTS.getTitle());
        }
    }
}