package com.uca.pncsegundoparcialbiblioteca.repository;

import com.uca.pncsegundoparcialbiblioteca.entity.Book;
import com.uca.pncsegundoparcialbiblioteca.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    List<Book> findByGenre(Genre genre);

    List<Book> findByAvailable(Boolean available);

    List<Book> findByGenreAndAvailable(Genre genre, Boolean available);
}
