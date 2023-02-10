package com.osipov.kameleoontrialtask.controller;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface QuoteController {
    Optional<QuoteOutDto> create(QuoteInDto quoteInDto);

    Optional<QuoteOutDto> update(Long id, QuoteInDto quoteInDto);

    Optional<QuoteOutDto> get(Long id);

    List<QuoteFullDto> getTop10(String order);

    void delete(Long id);

    Optional<QuoteOutDto> getRandomQuote() throws NoSuchAlgorithmException;
}