package com.osipov.kameleoontrialtask.service;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;

import java.util.List;

public interface VoteQuoteService {

    void vote(VoteQuoteInDto voteQuoteInDto);

    List<Integer> getScoreHistory(Long quoteId);
}