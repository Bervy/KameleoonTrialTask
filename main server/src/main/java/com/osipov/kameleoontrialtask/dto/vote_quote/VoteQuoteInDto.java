package com.osipov.kameleoontrialtask.dto.vote_quote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuoteInDto {

    @NotNull
    @Positive
    private long userId;

    @NotNull
    @Positive
    private long quoteId;

    private boolean vote;
}