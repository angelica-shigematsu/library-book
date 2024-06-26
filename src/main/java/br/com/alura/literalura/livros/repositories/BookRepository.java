package br.com.alura.literalura.livros.repositories;

import br.com.alura.literalura.livros.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<List<Book>> findByLanguagesContainingIgnoreCase(String language);

    Optional<List<Book>> findTop10ByOrderByDownloadCountDesc();

}
