package com.osipov.kameleoontrialtask.dto.vote_quote;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VoteQuoteInDto {

    @NotNull
    @Positive
    private long userId;

    @NotNull
    @Positive
    private long quoteId;

    private boolean vote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteQuoteInDto that = (VoteQuoteInDto) o;
        return userId == that.userId && quoteId == that.quoteId && vote == that.vote;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quoteId, vote);
    }
}