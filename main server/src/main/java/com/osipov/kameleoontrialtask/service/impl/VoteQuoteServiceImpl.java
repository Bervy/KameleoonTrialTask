package com.osipov.kameleoontrialtask.service.impl;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import com.osipov.kameleoontrialtask.error.NotFoundException;
import com.osipov.kameleoontrialtask.mapper.VoteQuoteMapper;
import com.osipov.kameleoontrialtask.model.quote.Quote;
import com.osipov.kameleoontrialtask.model.user.User;
import com.osipov.kameleoontrialtask.model.vote_quote.VoteQuote;
import com.osipov.kameleoontrialtask.repository.QuoteRepository;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import com.osipov.kameleoontrialtask.repository.VoteQuoteRepository;
import com.osipov.kameleoontrialtask.service.VoteQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.osipov.kameleoontrialtask.constants.Constants.*;
import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.QUOTE_NOT_FOUND;
import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteQuoteServiceImpl implements VoteQuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteQuoteRepository voteQuoteRepository;
    private final UserRepository userRepository;
    private final VoteQuoteMapper voteQuoteMapper;

    @Override
    public void vote(VoteQuoteInDto voteQuoteInDto) {
        Optional<VoteQuote> voteQuoteFromDb = getValidVoteQuoteByUserIdAndQuoteId(voteQuoteInDto);
        Quote quote = getValidQuote(voteQuoteInDto);
        if (voteQuoteFromDb.isPresent()) {
            if (voteQuoteFromDb.get().isVote() && !voteQuoteInDto.isVote()) {
                setScoreAfterVote(voteQuoteFromDb.get(), quote.getScore() + DECREASE_SCORE_BY_2);
                updateScoreAndSaveVote(DECREASE_SCORE_BY_2, voteQuoteFromDb.get());
            }
            if (!voteQuoteFromDb.get().isVote() && voteQuoteInDto.isVote()) {
                setScoreAfterVote(voteQuoteFromDb.get(), quote.getScore() + INCREASE_SCORE_BY_2);
                updateScoreAndSaveVote(INCREASE_SCORE_BY_2, voteQuoteFromDb.get());
            }
        } else {
            User user = userRepository.findById(voteQuoteInDto.getUserId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getTitle()));
            VoteQuote voteQuote = voteQuoteMapper.dtoToVoteQuote(voteQuoteInDto);
            voteQuote.setQuote(quote);
            voteQuote.setUser(user);
            if (voteQuoteInDto.isVote()) {
                setScoreAfterVote(voteQuote, quote.getScore() + INCREASE_SCORE_BY_1);
                updateScoreAndSaveVote(INCREASE_SCORE_BY_1, voteQuote);

            } else {
                setScoreAfterVote(voteQuote, quote.getScore() + DECREASE_SCORE_BY_1);
                updateScoreAndSaveVote(DECREASE_SCORE_BY_1, voteQuote);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getScoreHistory(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        return quote.getVotes().stream()
                .sorted(Comparator.comparing(VoteQuote::getUpdatedAt))
                .map(voteQuoteMapper::voteQuoteToInteger)
                .collect(Collectors.toList());
    }

    private Quote getValidQuote(VoteQuoteInDto voteQuoteInDto) {
        return quoteRepository.findById(voteQuoteInDto.getQuoteId())
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
    }

    private Optional<VoteQuote> getValidVoteQuoteByUserIdAndQuoteId(VoteQuoteInDto voteQuoteInDto) {
        return voteQuoteRepository
                .findByQuoteIdAndUserId(voteQuoteInDto.getQuoteId(), voteQuoteInDto.getUserId());
    }

    private void updateScoreAndSaveVote(int vote, VoteQuote voteQuote) {
        quoteRepository.updateScore(vote, LocalDateTime.now(), voteQuote.getQuote().getId());
        voteQuoteRepository.save(voteQuote);
    }

    private void setScoreAfterVote(VoteQuote voteQuote, int scoreAfterVote) {
        voteQuote.setScoreAfterVote(scoreAfterVote);
    }
}