package com.osipov.kameleoontrialtask.controller.impl;

import com.osipov.kameleoontrialtask.controller.VoteQuoteController;
import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import com.osipov.kameleoontrialtask.service.VoteQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class VoteQuoteControllerImpl implements VoteQuoteController {

    private final VoteQuoteService voteQuoteService;

    @PostMapping("/votes")
    @Override
    public void vote(@Valid @RequestBody VoteQuoteInDto voteQuoteInDto) {
        voteQuoteService.vote(voteQuoteInDto);
    }

    @GetMapping("{quoteId}/score-history")
    @Override
    public List<Integer> getScoreHistory(@PathVariable long quoteId) {
        return voteQuoteService.getScoreHistory(quoteId);
    }
}