package com.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String author;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;
    
}
