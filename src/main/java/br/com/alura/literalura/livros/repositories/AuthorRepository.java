package br.com.alura.literalura.livros.repositories;

import br.com.alura.literalura.livros.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameContainingIgnoreCase(String name);

    Optional<List<Author>> findByDeathYearLessThan(int year);
}
