package com.uca.pncsegundoparcialbiblioteca.service.impl;

import com.uca.pncsegundoparcialbiblioteca.dto.BookRequestDTO;
import com.uca.pncsegundoparcialbiblioteca.dto.BookResponseDTO;
import com.uca.pncsegundoparcialbiblioteca.entity.Book;
import com.uca.pncsegundoparcialbiblioteca.entity.Genre;
import com.uca.pncsegundoparcialbiblioteca.exception.BusinessRuleException;
import com.uca.pncsegundoparcialbiblioteca.exception.ResourceNotFoundException;
import com.uca.pncsegundoparcialbiblioteca.repository.BookRepository;
import com.uca.pncsegundoparcialbiblioteca.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponseDTO create(BookRequestDTO dto) {
        if (bookRepository.existsByTitleIgnoreCase(dto.getTitle())) {
            throw new BusinessRuleException("Ya existe un libro con el título: " + dto.getTitle());
        }
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BusinessRuleException("Ya existe un libro con el ISBN: " + dto.getIsbn());
        }
        if (dto.getPublishedDate() != null && dto.getPublishedDate().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("La fecha de publicación no puede ser futura");
        }

        int availableCopies = dto.getAvailableCopies() != null
                ? dto.getAvailableCopies()
                : dto.getTotalCopies();

        if (availableCopies > dto.getTotalCopies()) {
            throw new BusinessRuleException("Las copias disponibles no pueden superar el total de copias");
        }

        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .genre(dto.getGenre())
                .totalCopies(dto.getTotalCopies())
                .availableCopies(availableCopies)
                .available(availableCopies > 0)
                .publishedDate(dto.getPublishedDate())
                .description(dto.getDescription())
                .build();

        return toResponse(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDTO> findAll(Genre genre, Boolean available) {
        List<Book> books;

        if (genre != null && available != null) {
            books = bookRepository.findByGenreAndAvailable(genre, available);
        } else if (genre != null) {
            books = bookRepository.findByGenre(genre);
        } else if (available != null) {
            books = bookRepository.findByAvailable(available);
        } else {
            books = bookRepository.findAll();
        }

        return books.stream().map(this::toResponse).toList();
    }

    @Override
    public BookResponseDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }

    @Override
    public BookResponseDTO update(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        if (bookRepository.existsByTitleIgnoreCaseAndIdNot(dto.getTitle(), id)) {
            throw new BusinessRuleException("Ya existe un libro con el título: " + dto.getTitle());
        }
        if (bookRepository.existsByIsbnAndIdNot(dto.getIsbn(), id)) {
            throw new BusinessRuleException("Ya existe un libro con el ISBN: " + dto.getIsbn());
        }
        if (dto.getPublishedDate() != null && dto.getPublishedDate().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("La fecha de publicación no puede ser futura");
        }

        int availableCopies = dto.getAvailableCopies() != null
                ? dto.getAvailableCopies()
                : book.getAvailableCopies();

        if (availableCopies > dto.getTotalCopies()) {
            throw new BusinessRuleException("Las copias disponibles no pueden superar el total de copias");
        }
        if (availableCopies < 0) {
            throw new BusinessRuleException("Las copias disponibles no pueden ser negativas");
        }

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setGenre(dto.getGenre());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(availableCopies);
        book.setAvailable(availableCopies > 0);
        book.setPublishedDate(dto.getPublishedDate());
        book.setDescription(dto.getDescription());

        return toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        if (book.getAvailableCopies() < book.getTotalCopies()) {
            throw new BusinessRuleException(
                    "No se puede eliminar el libro porque tiene copias prestadas");
        }

        bookRepository.delete(book);
    }

    private BookResponseDTO toResponse(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .genre(book.getGenre())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .available(book.getAvailable())
                .publishedDate(book.getPublishedDate())
                .description(book.getDescription())
                .build();
    }
}
