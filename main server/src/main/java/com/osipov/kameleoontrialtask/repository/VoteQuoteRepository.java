package com.osipov.kameleoontrialtask.repository;

import com.osipov.kameleoontrialtask.model.quote.Quote;
import com.osipov.kameleoontrialtask.model.vote_quote.VoteQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteQuoteRepository extends JpaRepository<VoteQuote, Long> {

    @Modifying
    void deleteByQuote(Quote quote);

    Optional<VoteQuote> findByQuoteIdAndUserId(Long quoteId, Long userId);
}