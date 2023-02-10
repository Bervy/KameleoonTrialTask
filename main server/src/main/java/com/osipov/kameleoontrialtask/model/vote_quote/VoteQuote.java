package com.osipov.kameleoontrialtask.model.vote_quote;

import com.osipov.kameleoontrialtask.model.quote.Quote;
import com.osipov.kameleoontrialtask.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vote_quotes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VoteQuote {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Quote quote;

    @Column(name = "updated_score")
    private int scoreAfterVote;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean vote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteQuote voteQuote = (VoteQuote) o;
        return Objects.equals(id, voteQuote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}