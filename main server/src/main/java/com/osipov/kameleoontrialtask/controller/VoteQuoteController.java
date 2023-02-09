package com.osipov.kameleoontrialtask.controller;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import com.osipov.kameleoontrialtask.service.VoteQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class VoteQuoteController {

    private final VoteQuoteService voteQuoteService;

    @PostMapping("/votes")
    public void vote(@Valid @RequestBody VoteQuoteInDto voteQuoteInDto) {
        voteQuoteService.vote(voteQuoteInDto);
    }

    @GetMapping("{quoteId}/score-history")
    public List<Integer> getScoreHistory(@PathVariable long quoteId) {
        return voteQuoteService.getScoreHistory(quoteId);
    }
}