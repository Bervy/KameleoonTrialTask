package com.osipov.kameleoontrialtask.vote_quote;

import com.osipov.kameleoontrialtask.controller.impl.VoteQuoteControllerImpl;
import com.osipov.kameleoontrialtask.service.impl.VoteQuoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VoteQuoteControllerImpl.class)
class VoteQuoteControllerTests {
    @MockBean
    private VoteQuoteServiceImpl voteQuoteService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetScoreHistory() throws Exception {
        when(voteQuoteService.getScoreHistory(anyLong())).thenReturn(List.of(1));

        mockMvc.perform(get("/quotes/1/score-history")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$.[0]", is(1), Integer.class));
    }
}