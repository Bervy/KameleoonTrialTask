package com.osipov.kameleoontrialtask.controller.impl;

import com.osipov.kameleoontrialtask.controller.QuoteController;
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
import java.util.Optional;

import static com.osipov.kameleoontrialtask.error.ExceptionDescriptions.UNKNOWN_TYPE_OF_SORT;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteControllerImpl implements QuoteController {

    private final QuoteService quoteService;

    @PostMapping
    @Override
    public Optional<QuoteOutDto> create(@Valid @RequestBody QuoteInDto quoteInDto) {
        return quoteService.create(quoteInDto);
    }

    @PutMapping("/{id}")
    @Override
    public Optional<QuoteOutDto> update(@NotNull @Positive @PathVariable Long id, @Valid @RequestBody QuoteInDto quoteInDto) {
        return quoteService.update(id, quoteInDto);
    }

    @GetMapping("/{id}")
    @Override
    public Optional<QuoteOutDto> get(@NotNull @Positive @PathVariable Long id) {
        return quoteService.get(id);
    }

    @GetMapping("/top10")
    @Override
    public List<QuoteFullDto> getTop10(@RequestParam(defaultValue = "TOP") String order) {
        SortType sortType = SortType.from(order)
                .orElseThrow(() -> new IllegalArgumentException(UNKNOWN_TYPE_OF_SORT.getTitle()));
        return quoteService.getTop(sortType);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@NotNull @Positive @PathVariable Long id) {
        quoteService.delete(id);
    }

    @GetMapping("/random")
    @Override
    public Optional<QuoteOutDto> getRandomQuote() throws NoSuchAlgorithmException {
        return quoteService.getRandomQuote();
    }
}