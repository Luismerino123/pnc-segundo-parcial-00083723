package com.uca.pncsegundoparcialbiblioteca.service;

import com.uca.pncsegundoparcialbiblioteca.dto.BookRequestDTO;
import com.uca.pncsegundoparcialbiblioteca.dto.BookResponseDTO;
import com.uca.pncsegundoparcialbiblioteca.entity.Genre;

import java.util.List;

public interface BookService {

    BookResponseDTO create(BookRequestDTO dto);

    List<BookResponseDTO> findAll(Genre genre, Boolean available);

    BookResponseDTO findById(Long id);

    BookResponseDTO update(Long id, BookRequestDTO dto);

    void delete(Long id);
}
