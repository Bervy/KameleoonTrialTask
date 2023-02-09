package com.osipov.kameleoontrialtask.service;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import com.osipov.kameleoontrialtask.error.NotFoundException;
import com.osipov.kameleoontrialtask.mapper.VoteQuoteMapper;
import com.osipov.kameleoontrialtask.model.Quote;
import com.osipov.kameleoontrialtask.model.VoteQuote;
import com.osipov.kameleoontrialtask.repository.QuoteRepository;
import com.osipov.kameleoontrialtask.repository.VoteQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.QUOTE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteQuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteQuoteRepository voteQuoteRepository;

    @Transactional
    public void vote(VoteQuoteInDto voteQuoteInDto) {
        Optional<VoteQuote> voteQuote = voteQuoteRepository
                .findByQuoteIdAndUserId(voteQuoteInDto.getQuoteId(), voteQuoteInDto.getUserId());
        if (voteQuote.isPresent()) {
            if (voteQuote.get().isVote() && !voteQuoteInDto.isVote()) {
                updateScoreAndSaveVote(-2, voteQuoteInDto);
            }
            if (!voteQuote.get().isVote() && voteQuoteInDto.isVote()) {
                updateScoreAndSaveVote(2, voteQuoteInDto);
            }
        } else {
            if (voteQuoteInDto.isVote()) {
                updateScoreAndSaveVote(1, voteQuoteInDto);
            } else {
                updateScoreAndSaveVote(-1, voteQuoteInDto);
            }
        }
    }

    private void updateScoreAndSaveVote(int vote, VoteQuoteInDto voteQuoteInDto) {
        quoteRepository.updateScore(vote, LocalDateTime.now(), voteQuoteInDto.getQuoteId());
        voteQuoteRepository.saveAndFlush(VoteQuoteMapper.dtoToVoteQuote(voteQuoteInDto));
    }

    @Transactional(readOnly = true)
    public List<Integer> getScoreHistory(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        return quote.getVotes().stream()
                .sorted(Comparator.comparing(VoteQuote::getUpdatedAt))
                .map(VoteQuoteMapper::voteQuoteToInteger)
                .collect(Collectors.toList());
    }
}
