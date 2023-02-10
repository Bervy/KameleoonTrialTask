package com.osipov.kameleoontrialtask.quote;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.mapper.QuoteMapper;
import com.osipov.kameleoontrialtask.model.quote.Quote;
import com.osipov.kameleoontrialtask.model.quote.SortType;
import com.osipov.kameleoontrialtask.model.user.User;
import com.osipov.kameleoontrialtask.repository.QuoteRepository;
import com.osipov.kameleoontrialtask.repository.UserRepository;
import com.osipov.kameleoontrialtask.repository.VoteQuoteRepository;
import com.osipov.kameleoontrialtask.service.impl.QuoteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteUnitTests {
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private final QuoteInDto quoteInDto = QuoteInDto.builder()
            .text("text")
            .build();
    private final QuoteOutDto quoteOutDto = QuoteOutDto.builder()
            .id(ID_1)
            .text("text")
            .build();
    private final Quote quote1 = Quote.builder()
            .id(ID_1)
            .text("text")
            .build();
    private final Quote quote2 = Quote.builder()
            .id(ID_2)
            .text("text")
            .build();
    private final QuoteFullDto quoteFullDto1 = QuoteFullDto.builder()
            .id(ID_1)
            .text("text")
            .build();
    private final QuoteFullDto quoteFullDto2 = QuoteFullDto.builder()
            .id(ID_2)
            .text("text")
            .build();
    private final User user = User.builder()
            .id(ID_1)
            .name("userName2")
            .email("userMail2@ya.ru")
            .build();
    @Mock
    private QuoteMapper quoteMapper;
    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VoteQuoteRepository voteQuoteRepository;
    @InjectMocks
    private QuoteServiceImpl quoteService;

    @Test
    void testCreateQuote() {
        Mockito.when(quoteRepository.save(any())).thenReturn(quote1);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        Mockito.when(quoteMapper.dtoToQuote(quoteInDto)).thenReturn(quote1);
        Mockito.when(quoteMapper.quoteToDto(quote1)).thenReturn(quoteOutDto);

        Optional<QuoteOutDto> quoteDtoResult = quoteService.create(quoteInDto);

        assertTrue(quoteDtoResult.isPresent());
        assertEquals(ID_1, quoteDtoResult.get().getId());
        verify(quoteRepository, times(1)).save(quote1);
    }

    @Test
    void testUpdateQuoteById() {
        Mockito.when(quoteRepository.save(any())).thenReturn(quote1);
        Mockito.when(quoteRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(quote1));
        Mockito.when(quoteMapper.quoteToDto(quote1)).thenReturn(quoteOutDto);

        Optional<QuoteOutDto> quoteDtoResult = quoteService.update(ID_1, quoteInDto);

        assertTrue(quoteDtoResult.isPresent());
        assertEquals(ID_1, quoteDtoResult.get().getId());
        verify(quoteRepository, times(1)).save(quote1);
    }

    @Test
    void testGetQuoteById() {
        Mockito.when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quote1));
        Mockito.when(quoteMapper.quoteToDto(quote1)).thenReturn(quoteOutDto);

        Optional<QuoteOutDto> quoteDtoResult = quoteService.get(ID_1);

        assertTrue(quoteDtoResult.isPresent());
        assertEquals(ID_1, quoteDtoResult.get().getId());
        verify(quoteRepository, times(1)).findById(ID_1);
    }

    @Test
    void testGetTop10Quotes() {
        Mockito.when(quoteRepository.findTop10ByOrderByScoreAsc()).thenReturn(List.of(quote1, quote2));
        Mockito.when(quoteMapper.quotesToListFullDto(List.of(quote1, quote2)))
                .thenReturn(List.of(quoteFullDto1, quoteFullDto2));

        List<QuoteFullDto> quotesResult = quoteService.getTop(SortType.TOP);

        int expectedSize = 2;
        Assertions.assertNotNull(quotesResult);
        assertEquals(expectedSize, quotesResult.size());
        verify(quoteRepository, times(1)).findTop10ByOrderByScoreAsc();
    }

    @Test
    void testDeleteQuote() {
        Mockito.when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quote1));
        quoteService.delete(ID_1);

        Mockito.verify(quoteRepository, times(1)).delete(quote1);
        Mockito.verify(voteQuoteRepository, times(1)).deleteByQuote(quote1);
    }

    @Test
    void testGetGetRandomQuote() throws NoSuchAlgorithmException {
        Mockito.when(quoteRepository.findAll()).thenReturn(List.of(quote1));
        Mockito.when(quoteMapper.quoteToDto(quote1)).thenReturn(quoteOutDto);

        Optional<QuoteOutDto> quoteDtoResult = quoteService.getRandomQuote();

        assertTrue(quoteDtoResult.isPresent());
        assertEquals(ID_1, quoteDtoResult.get().getId());
        verify(quoteRepository, times(1)).findAll();
    }
}