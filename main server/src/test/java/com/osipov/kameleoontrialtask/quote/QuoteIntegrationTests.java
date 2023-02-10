package com.osipov.kameleoontrialtask.quote;

import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.dto.user.UserInDto;
import com.osipov.kameleoontrialtask.mapper.QuoteMapper;
import com.osipov.kameleoontrialtask.model.quote.Quote;
import com.osipov.kameleoontrialtask.model.user.User;
import com.osipov.kameleoontrialtask.service.impl.QuoteServiceImpl;
import com.osipov.kameleoontrialtask.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class QuoteIntegrationTests {
    private final QuoteServiceImpl quoteService;
    private final UserServiceImpl userService;
    private final QuoteMapper quoteMapper;
    private final User user = User.builder()
            .id(1L)
            .name("userName1")
            .password("password")
            .email("userMail1@ya.ru")
            .build();
    private final Quote mockQuote = Quote.builder()
            .user(user)
            .text("text")
            .build();
    private final UserInDto userInDto = UserInDto.builder()
            .name("userName1")
            .email("userMail1@ya.ru")
            .password("password")
            .build();


    @Test
    void testFindQuoteById() {
        userService.create(userInDto);
        quoteService.create(quoteMapper.quoteToInDto(mockQuote));

        Optional<QuoteOutDto> quoteOutDto = quoteService.get(1L);

        assertThat(quoteOutDto.isPresent(), is(true));
        assertThat(quoteOutDto.get().getText(), equalTo(mockQuote.getText()));
    }
}