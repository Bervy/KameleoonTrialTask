package com.osipov.kameleoontrialtask.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "QUOTES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quote {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "score", nullable = false, columnDefinition = "int default 0")
    private int score;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quote")
    private List<VoteQuote> votes;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}