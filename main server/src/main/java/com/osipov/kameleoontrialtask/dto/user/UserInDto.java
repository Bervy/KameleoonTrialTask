package com.osipov.kameleoontrialtask.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInDto {

    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    @NotBlank
    String password;
}