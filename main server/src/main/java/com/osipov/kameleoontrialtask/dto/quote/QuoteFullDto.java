package com.osipov.kameleoontrialtask.dto.quote;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteFullDto {
    private Long id;
    private String userName;
    private int numberOfVotes;
    private String text;
}