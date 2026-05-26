package com.uca.pncsegundoparcialbiblioteca.dto;

import com.uca.pncsegundoparcialbiblioteca.entity.Genre;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    private String author;

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^(\\d{9}[\\dX]|\\d{13})$", message = "ISBN debe tener 10 o 13 dígitos")
    private String isbn;

    @NotNull(message = "El género es obligatorio")
    private Genre genre;

    @NotNull(message = "El total de copias es obligatorio")
    @Min(value = 1, message = "El total de copias debe ser al menos 1")
    private Integer totalCopies;

    @Min(value = 0, message = "Las copias disponibles no pueden ser negativas")
    private Integer availableCopies;

    @PastOrPresent(message = "La fecha de publicación no puede ser futura")
    private LocalDate publishedDate;

    private String description;
}
