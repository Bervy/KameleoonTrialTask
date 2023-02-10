package com.osipov.kameleoontrialtask.service;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.model.quote.SortType;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface QuoteService {

    Optional<QuoteOutDto> create(QuoteInDto quoteInDto);

    Optional<QuoteOutDto> update(Long id, QuoteInDto quoteInDto);

    Optional<QuoteOutDto> get(Long id);

    List<QuoteFullDto> getTop(SortType sortType);

    void delete(Long id);

    Optional<QuoteOutDto> getRandomQuote() throws NoSuchAlgorithmException;
}