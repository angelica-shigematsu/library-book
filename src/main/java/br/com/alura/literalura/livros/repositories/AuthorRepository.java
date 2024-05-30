package br.com.alura.literalura.livros.repositories;

import br.com.alura.literalura.livros.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
