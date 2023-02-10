package com.osipov.kameleoontrialtask.service.impl;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.error.ConflictException;
import com.osipov.kameleoontrialtask.error.NotFoundException;
import com.osipov.kameleoontrialtask.mapper.QuoteMapper;
import com.osipov.kameleoontrialtask.model.quote.Quote;
import com.osipov.kameleoontrialtask.model.quote.SortType;
import com.osipov.kameleoontrialtask.model.user.User;
import com.osipov.kameleoontrialtask.repository.QuoteRepository;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import com.osipov.kameleoontrialtask.repository.VoteQuoteRepository;
import com.osipov.kameleoontrialtask.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.*;

@Service
@RequiredArgsConstructor
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteQuoteRepository voteQuoteRepository;
    private final UserRepository userRepository;
    private final QuoteMapper quoteMapper;

    @Override
    public Optional<QuoteOutDto> create(QuoteInDto quoteInDto) {
        User user = getUserFromRepository(quoteInDto.getUserId());
        Quote quote = quoteMapper.dtoToQuote(quoteInDto);
        quote.setUser(user);
        try {
            return Optional.ofNullable(quoteMapper.quoteToDto(quoteRepository.save(quote)));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(QUOTE_ALREADY_EXISTS.getTitle());
        }
    }

    @Override
    public Optional<QuoteOutDto> update(Long id, QuoteInDto quoteInDto) {
        Quote quote = getQuoteFromRepositoryByUserId(id, quoteInDto.getUserId());
        quote.setUpdatedAt(LocalDateTime.now());
        quote.setText(quoteInDto.getText());
        return Optional.ofNullable(quoteMapper.quoteToDto(quoteRepository.save(quote)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteOutDto> get(Long id) {
        return Optional.ofNullable(quoteMapper.quoteToDto(quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteFullDto> getTop(SortType sortType) {
        List<Quote> quotes = sortType == SortType.TOP ? quoteRepository.findTop10ByOrderByScoreAsc()
                : quoteRepository.findTop10ByOrderByScoreDesc();
        return quoteMapper.quotesToListFullDto(quotes);
    }

    @Override
    public void delete(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        if (quote.getVotes() == null) {
            voteQuoteRepository.deleteByQuote(quote);
        }
        quoteRepository.delete(quote);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteOutDto> getRandomQuote() throws NoSuchAlgorithmException {
        List<Quote> quotes = quoteRepository.findAll();
        if (quotes.isEmpty()) {
            throw new NotFoundException(QUOTE_NOT_FOUND.getTitle());
        } else {
            int random = SecureRandom.getInstanceStrong().nextInt(quotes.size());
            Quote quote = quotes.get(random);
            return Optional.ofNullable(quoteMapper.quoteToDto(quote));
        }
    }

    private User getUserFromRepository(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getTitle()));
    }

    private Quote getQuoteFromRepositoryByUserId(Long quoteId, Long userId) {
        return quoteRepository.findByIdAndUserId(quoteId, userId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
    }
}