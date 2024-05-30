package br.com.alura.literalura.livros;

import br.com.alura.literalura.livros.repositories.AuthorRepository;
import br.com.alura.literalura.livros.repositories.BookRepository;
import br.com.alura.literalura.livros.web.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class LivrosApplication implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LivrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository, authorRepository);
		main.showMenu();
	}
}
