package com.osipov.kameleoontrialtask.service;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.error.ConflictException;
import com.osipov.kameleoontrialtask.error.NotFoundException;
import com.osipov.kameleoontrialtask.mapper.QuoteMapper;
import com.osipov.kameleoontrialtask.model.Quote;
import com.osipov.kameleoontrialtask.model.User;
import com.osipov.kameleoontrialtask.model.quote.SortType;
import com.osipov.kameleoontrialtask.repository.QuoteRepository;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import com.osipov.kameleoontrialtask.repository.VoteQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.*;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteQuoteRepository voteQuoteRepository;
    private final UserRepository userRepository;

    public QuoteOutDto create(QuoteInDto quoteInDto) {
        User user = getUserFromRepository(quoteInDto.getUserId());
        Quote quote = QuoteMapper.dtoToQuote(quoteInDto);
        quote.setUser(user);
        try {
            return QuoteMapper.quoteToDto(quoteRepository.save(quote));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(QUOTE_ALREADY_EXISTS.getTitle());
        }
    }

    public QuoteOutDto update(Long id, QuoteInDto quoteInDto) {
        Quote quote = getQuoteFromRepositoryByUserId(id, quoteInDto.getUserId());
        return QuoteMapper.quoteToDto(quoteRepository.saveAndFlush(quote));
    }

    public QuoteOutDto get(Long id) {
        return QuoteMapper.quoteToDto(quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle())));
    }

    @Transactional(readOnly = true)
    public List<QuoteFullDto> getTop(SortType sortType) {
        List<Quote> quotes = sortType == SortType.TOP? quoteRepository.findTop10ByOrderByScoreAsc()
                : quoteRepository.findTop10ByOrderByScoreDesc();
        return QuoteMapper.eventToListOutDto(quotes);
    }

    @Transactional
    public void delete(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        voteQuoteRepository.deleteByQuote(quote);
        quoteRepository.delete(quote);
    }

    private User getUserFromRepository(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getTitle()));
    }

    private Quote getQuoteFromRepositoryByUserId(Long quoteId, Long userId) {
        return quoteRepository.findByIdAndUserId(quoteId, userId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
    }

    @Transactional(readOnly = true)
    public QuoteOutDto getRandomQuote() throws NoSuchAlgorithmException {
        List<Quote> quotes = quoteRepository.findAll();
        int random = SecureRandom.getInstanceStrong().nextInt(quotes.size());
        return QuoteMapper.quoteToDto(quotes.get(random));
    }
}