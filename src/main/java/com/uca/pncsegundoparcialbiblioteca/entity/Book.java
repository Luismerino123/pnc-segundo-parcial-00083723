package com.uca.pncsegundoparcialbiblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private Integer totalCopies;

    @Column(nullable = false)
    private Integer availableCopies;

    @Column(nullable = false)
    private Boolean available;

    @Column
    private LocalDate publishedDate;

    @Column(length = 1000)
    private String description;
}
