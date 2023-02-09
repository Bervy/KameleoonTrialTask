package com.osipov.kameleoontrialtask.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutDto {

    private Long id;
    private String name;
    private String email;
}