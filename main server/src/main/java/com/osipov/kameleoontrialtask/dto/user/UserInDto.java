package com.osipov.kameleoontrialtask.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserInDto {

    @NotBlank
    String password;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInDto userInDto = (UserInDto) o;
        return Objects.equals(name, userInDto.name) && Objects.equals(email, userInDto.email)
                && Objects.equals(password, userInDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
}