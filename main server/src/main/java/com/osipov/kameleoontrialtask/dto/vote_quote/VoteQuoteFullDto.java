package com.osipov.kameleoontrialtask.dto.vote_quote;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteQuoteFullDto {

    private long userId;
    private int vote;
    private LocalDateTime createdAt;
}