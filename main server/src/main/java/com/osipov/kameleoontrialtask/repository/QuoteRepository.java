package com.osipov.kameleoontrialtask.repository;

import com.osipov.kameleoontrialtask.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Modifying
    @Query("update Quote set score=score + ?1,updatedAt=?2 where id = ?3")
    void updateScore(int vote, LocalDateTime updatedAt, Long id);

    Optional<Quote> findByIdAndUserId(Long eventId, Long userId);

    List<Quote> findTop10ByOrderByScoreAsc();

    List<Quote> findTop10ByOrderByScoreDesc();
}