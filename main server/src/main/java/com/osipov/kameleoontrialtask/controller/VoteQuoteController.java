package com.osipov.kameleoontrialtask.controller;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;

import java.util.List;

public interface VoteQuoteController {
    void vote(VoteQuoteInDto voteQuoteInDto);

    List<Integer> getScoreHistory(long quoteId);
}