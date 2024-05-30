package br.com.alura.literalura.livros.services;

import br.com.alura.literalura.livros.models.*;
import br.com.alura.literalura.livros.repositories.AuthorRepository;
import br.com.alura.literalura.livros.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookService {

    @Autowired
    static AuthorRepository authorRepository;

    @Autowired
    static BookRepository bookRepository;

    public BookService(BookRepository repository, AuthorRepository authorRepository) {
        BookService.authorRepository = authorRepository;
        bookRepository = repository;
    }


    private static ResponseDTO getData(String title) {
        ApiConsumption consumption = new ApiConsumption();
        ConveterData conveterData = new ConveterData();

        final String ADDRESS = "https://gutendex.com";

        var json = consumption.getData(ADDRESS + "/books/?search=" + title.replace(" ", "%20"));
        System.out.println(json);
        ResponseDTO dto = conveterData.getData(json, ResponseDTO.class);
        return dto;
    }


    public static void addBook() {
        Scanner scanner = new Scanner(System.in);
        DataAuthor author = null;
        System.out.println("Digite nome de um livro: ");
        String title = scanner.nextLine();

        ResponseDTO respDto = getData(title.toLowerCase());

        if (!respDto.results().isEmpty()) {
            DataBook dataBook = respDto.results().get(0);

            List<DataAuthor> listAuthor = new ArrayList<>();
            for (int i = 0; i < dataBook.authors().size(); i++) {
                listAuthor.add(
                        new DataAuthor(
                                dataBook.authors().get(i).name(),
                                dataBook.authors().get(i).birthYear(),
                                dataBook.authors().get(i).deathYear()
                        )
                );

                authorRepository.save(new Author(listAuthor.get(i)));
            }

            DataBook dataBook1 = new DataBook(dataBook.title(), listAuthor, dataBook.listLanguages(), dataBook.downloadCount());
            Book book = new Book(dataBook1);
            bookRepository.save(book);
            System.out.println("Salvo com sucesso");
        }
    }
}
