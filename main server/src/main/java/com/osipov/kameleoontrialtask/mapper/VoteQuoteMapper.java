package com.osipov.kameleoontrialtask.mapper;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import com.osipov.kameleoontrialtask.model.VoteQuote;

public class VoteQuoteMapper {

    public static Integer voteQuoteToInteger(VoteQuote voteQuote) {
        return voteQuote.getScoreAfterVote();
    }

    public static VoteQuote dtoToVoteQuote(VoteQuoteInDto voteQuoteInDto) {
        return VoteQuote.builder()
                .vote(voteQuoteInDto.isVote())
                .build();
    }
}