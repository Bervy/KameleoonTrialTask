package com.osipov.kameleoontrialtask.user;

import com.osipov.kameleoontrialtask.dto.user.UserOutDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserJsonTests {
    @Autowired
    private JacksonTester<UserOutDto> json;

    @Test
    void testUserJson() throws Exception {
        UserOutDto userDto = UserOutDto.builder()
                .id(1L)
                .name("userName1")
                .email("userMail1@ya.ru")
                .build();
        JsonContent<UserOutDto> resultJson = json.write(userDto);

        assertThat(resultJson).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(resultJson).extractingJsonPathStringValue("$.name")
                .isEqualTo(userDto.getName());
        assertThat(resultJson).extractingJsonPathStringValue("$.email")
                .isEqualTo(userDto.getEmail());
    }
}