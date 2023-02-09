package com.osipov.kameleoontrialtask.controller;

import com.osipov.kameleoontrialtask.dto.quote.QuoteFullDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteInDto;
import com.osipov.kameleoontrialtask.dto.quote.QuoteOutDto;
import com.osipov.kameleoontrialtask.model.quote.SortType;
import com.osipov.kameleoontrialtask.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.UNKNOWN_TYPE_OF_SORT;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping
    public QuoteOutDto create(@Valid @RequestBody QuoteInDto quoteInDto) {
        return quoteService.create(quoteInDto);
    }

    @PutMapping("/{id}")
    public QuoteOutDto update(@NotNull @Positive @PathVariable Long id, @Valid @RequestBody QuoteInDto quoteInDto) {
        return quoteService.update(id, quoteInDto);
    }

    @GetMapping("/{id}")
    public QuoteOutDto get(@NotNull @Positive @PathVariable Long id) {
        return quoteService.get(id);
    }

    @GetMapping("/top10")
    public List<QuoteFullDto> getTop10(@RequestParam(defaultValue = "TOP") String order) {
        SortType sortType = SortType.from(order)
                .orElseThrow(() -> new IllegalArgumentException(UNKNOWN_TYPE_OF_SORT.getTitle()));
        return quoteService.getTop(sortType);
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @Positive @PathVariable Long id) {
        quoteService.delete(id);
    }

    @GetMapping(value = "random")
    public QuoteOutDto getRandomQuote() throws NoSuchAlgorithmException {
        return quoteService.getRandomQuote();
    }
}