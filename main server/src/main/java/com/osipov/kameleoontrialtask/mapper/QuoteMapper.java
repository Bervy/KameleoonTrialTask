package com.osipov.kameleoontrialtask.mapper;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.model.quote.Quote;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuoteMapper {

    public QuoteOutDto quoteToDto(Quote quote) {
        return QuoteOutDto.builder()
                .id(quote.getId())
                .text(quote.getText())
                .build();
    }

    public QuoteInDto quoteToInDto(Quote quote) {
        return QuoteInDto.builder()
                .userId(quote.getUser().getId())
                .text(quote.getText())
                .build();
    }

    public QuoteFullDto quoteToFullDto(Quote quote) {
        return QuoteFullDto.builder()
                .id(quote.getId())
                .userName(quote.getUser().getName())
                .score(quote.getScore())
                .text(quote.getText())
                .build();
    }

    public Quote dtoToQuote(QuoteInDto quoteInDto) {
        return Quote.builder()
                .text(quoteInDto.getText())
                .votes(new ArrayList<>())
                .score(0)
                .dateOfCreation(LocalDateTime.now())
                .build();
    }

    public List<QuoteFullDto> quotesToListFullDto(List<Quote> quotes) {
        return quotes.stream().map(this::quoteToFullDto).collect(Collectors.toList());
    }
}