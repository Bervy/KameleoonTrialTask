package com.osipov.kameleoontrialtask.mapper;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.model.Quote;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteMapper {

    public static QuoteOutDto quoteToDto(Quote quote) {
        return QuoteOutDto.builder()
                .id(quote.getId())
                .text(quote.getText())
                .build();
    }

    public static QuoteFullDto quoteToFullDto(Quote quote) {
        return QuoteFullDto.builder()
                .id(quote.getId())
                .userName(quote.getUser().getName())
                .numberOfVotes(quote.getScore())
                .text(quote.getText())
                .build();
    }

    public static Quote dtoToQuote(QuoteInDto quoteInDto) {
        return Quote.builder()
                .text(quoteInDto.getText())
                .build();
    }

    public static List<QuoteFullDto> eventToListOutDto(List<Quote> quotes) {
        return quotes.stream().map(QuoteMapper::quoteToFullDto).collect(Collectors.toList());
    }
}
