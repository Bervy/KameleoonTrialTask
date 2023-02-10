package com.osipov.kameleoontrialtask.dto.quote;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuoteInDto {

    @NotNull
    @Positive
    private long userId;
    @NotBlank
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteInDto that = (QuoteInDto) o;
        return userId == that.userId && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, text);
    }
}