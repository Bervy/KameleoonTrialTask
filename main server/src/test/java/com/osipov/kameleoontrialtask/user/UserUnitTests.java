package com.osipov.kameleoontrialtask.user;

import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import com.osipov.kameleoontrialtask.mapper.UserMapper;
import com.osipov.kameleoontrialtask.model.user.User;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import com.osipov.kameleoontrialtask.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserUnitTests {
    private static final long ID_1 = 1L;
    private final User user1 = User.builder()
            .id(ID_1)
            .name("userName1")
            .email("userMail1@ya.ru")
            .build();
    private final UserInDto userInDto = UserInDto.builder()
            .name("userName1")
            .email("userMail1@ya.ru")
            .build();
    private final UserOutDto userOutDto = UserOutDto.builder()
            .id(ID_1)
            .name("userName1")
            .email("userMail1@ya.ru")
            .build();
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testAddUser() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);
        Mockito.when(userMapper.dtoToUser(userInDto)).thenReturn(user1);
        Mockito.when(userMapper.userToDto(user1)).thenReturn(userOutDto);

        Optional<UserOutDto> userDtoResult = userService.create(userInDto);

        assertTrue(userDtoResult.isPresent());
        assertEquals(ID_1, userDtoResult.get().getId());
        verify(userRepository, times(1)).save(user1);
    }
}