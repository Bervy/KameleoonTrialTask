package com.osipov.kameleoontrialtask.quote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osipov.kameleoontrialtask.controller.impl.QuoteControllerImpl;
import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.service.impl.QuoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = QuoteControllerImpl.class)
class QuoteControllerTests {
    private final QuoteOutDto quoteOutDto = QuoteOutDto.builder()
            .id(1L)
            .text("text")
            .build();
    private final QuoteInDto quoteInDto = QuoteInDto.builder()
            .userId(1L)
            .text("text")
            .build();
    private final QuoteFullDto quoteFullDto = QuoteFullDto.builder()
            .id(1L)
            .userName("userName")
            .text("text")
            .build();
    @MockBean
    private QuoteServiceImpl quoteService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateQuote() throws Exception {
        when(quoteService.create(any())).thenReturn(Optional.ofNullable(quoteOutDto));

        mockMvc.perform(post("/quotes")
                        .content(objectMapper.writeValueAsString(quoteInDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text", is(quoteOutDto.getText())));
    }

    @Test
    void testUpdateQuoteById() throws Exception {
        when(quoteService.update(anyLong(), any())).thenReturn(Optional.ofNullable(quoteOutDto));

        mockMvc.perform(put("/quotes/1")
                        .content(objectMapper.writeValueAsString(quoteInDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text", is(quoteOutDto.getText())));
    }

    @Test
    void testGetQuoteById() throws Exception {
        when(quoteService.get(anyLong())).thenReturn(Optional.ofNullable(quoteOutDto));

        mockMvc.perform(get("/quotes/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(quoteOutDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(quoteOutDto.getText())));
    }

    @Test
    void testGetTop10Quotes() throws Exception {
        when(quoteService.getTop(any())).thenReturn(List.of(quoteFullDto));

        mockMvc.perform(get("/quotes/top10")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$.[0].id", is(quoteFullDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].userName", is(quoteFullDto.getUserName())))
                .andExpect(jsonPath("$.[0].text", is(quoteFullDto.getText())));
    }

    @Test
    void testDeleteQuote() throws Exception {
        mockMvc.perform(delete("/quotes/1")).andExpect(status().isOk());
    }

    @Test
    void testGetGetRandomQuote() throws Exception {
        when(quoteService.getRandomQuote()).thenReturn(Optional.ofNullable(quoteOutDto));

        mockMvc.perform(get("/quotes/random")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text", is(quoteOutDto.getText())));
    }
}