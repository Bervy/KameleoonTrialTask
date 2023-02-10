package com.osipov.kameleoontrialtask.mapper;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import com.osipov.kameleoontrialtask.model.vote_quote.VoteQuote;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoteQuoteMapper {

    public Integer voteQuoteToInteger(VoteQuote voteQuote) {
        return voteQuote.getScoreAfterVote();
    }

    public VoteQuote dtoToVoteQuote(VoteQuoteInDto voteQuoteInDto) {
        return VoteQuote.builder()
                .updatedAt(LocalDateTime.now())
                .vote(voteQuoteInDto.isVote())
                .build();
    }
}