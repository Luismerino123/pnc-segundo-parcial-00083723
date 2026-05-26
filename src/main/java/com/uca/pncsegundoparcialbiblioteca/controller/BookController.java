package com.uca.pncsegundoparcialbiblioteca.controller;

import com.uca.pncsegundoparcialbiblioteca.dto.BookRequestDTO;
import com.uca.pncsegundoparcialbiblioteca.dto.BookResponseDTO;
import com.uca.pncsegundoparcialbiblioteca.entity.Genre;
import com.uca.pncsegundoparcialbiblioteca.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> findAll(
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Boolean available) {
        return ResponseEntity.ok(bookService.findAll(genre, available));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
