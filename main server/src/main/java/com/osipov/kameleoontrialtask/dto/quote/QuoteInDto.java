package com.osipov.kameleoontrialtask.dto.quote;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteInDto {

    @NotNull
    @Positive
    private long userId;

    @NotBlank
    private String text;
}