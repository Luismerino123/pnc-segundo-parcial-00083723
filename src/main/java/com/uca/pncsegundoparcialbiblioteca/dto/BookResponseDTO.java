package com.uca.pncsegundoparcialbiblioteca.dto;

import com.uca.pncsegundoparcialbiblioteca.entity.Genre;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Genre genre;
    private Integer totalCopies;
    private Integer availableCopies;
    private Boolean available;
    private LocalDate publishedDate;
    private String description;
}
