package com.osipov.kameleoontrialtask.repository;

import com.osipov.kameleoontrialtask.model.Quote;
import com.osipov.kameleoontrialtask.model.VoteQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VoteQuoteRepository extends JpaRepository<VoteQuote, Long> {

    @Transactional
    @Modifying
    void deleteByQuote(Quote quote);

    Optional<VoteQuote> findByQuoteIdAndUserId(Long quoteId, Long userId);
}
