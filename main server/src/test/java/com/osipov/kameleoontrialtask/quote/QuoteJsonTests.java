package com.osipov.kameleoontrialtask.quote;

import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class QuoteJsonTests {
    @Autowired
    private JacksonTester<QuoteOutDto> json;

    @Test
    void testUserJson() throws Exception {
        QuoteOutDto quoteOutDto = QuoteOutDto.builder()
                .id(1L)
                .text("text")
                .build();
        JsonContent<QuoteOutDto> resultJson = json.write(quoteOutDto);

        assertThat(resultJson).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(resultJson).extractingJsonPathStringValue("$.text")
                .isEqualTo(quoteOutDto.getText());
    }
}