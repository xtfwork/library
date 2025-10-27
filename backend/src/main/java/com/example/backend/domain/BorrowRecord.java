package com.example.backend.domain;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrow_at", nullable = false)
    private Instant borrowAt = Instant.now();

    @PrePersist
    void prePersist(){
        if(borrowAt == null) borrowAt = Instant.now();
    }

    @Column(name = "return_at")
    private Instant returnAt;
    
}
