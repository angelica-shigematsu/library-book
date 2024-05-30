package br.com.alura.literalura.livros.repositories;

import br.com.alura.literalura.livros.models.Book;
import br.com.alura.literalura.livros.models.DataBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
