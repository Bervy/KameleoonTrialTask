package com.osipov.kameleoontrialtask.vote_quote;

import com.osipov.kameleoontrialtask.dto.vote_quote.VoteQuoteInDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class VoteQuoteJsonTests {
    @Autowired
    private JacksonTester<VoteQuoteInDto> json;

    @Test
    void testUserJson() throws Exception {
        VoteQuoteInDto voteQuoteInDto = VoteQuoteInDto.builder()
                .userId(1L)
                .quoteId(1L)
                .vote(true)
                .build();
        JsonContent<VoteQuoteInDto> resultJson = json.write(voteQuoteInDto);

        assertThat(resultJson).extractingJsonPathNumberValue("$.userId").isEqualTo(1);
        assertThat(resultJson).extractingJsonPathNumberValue("$.quoteId")
                .isEqualTo(1);
        assertThat(resultJson).extractingJsonPathBooleanValue("$.vote")
                .isEqualTo(voteQuoteInDto.isVote());
    }
}