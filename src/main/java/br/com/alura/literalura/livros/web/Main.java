package br.com.alura.literalura.livros.web;


import br.com.alura.literalura.livros.models.*;
import br.com.alura.literalura.livros.repositories.AuthorRepository;
import br.com.alura.literalura.livros.repositories.BookRepository;
import br.com.alura.literalura.livros.services.ApiConsumption;
import br.com.alura.literalura.livros.services.ConveterData;

import java.util.*;

public class Main {
    AuthorRepository authorRepository;

    BookRepository bookRepository;
    Scanner scanner = new Scanner(System.in);

    Scanner scanner2 = new Scanner(System.in);

    ApiConsumption consumption = new ApiConsumption();
    ConveterData conveterData = new ConveterData();

    private final String ADDRESS = "https://gutendex.com";

    public Main(BookRepository repository, AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = repository;
    }

    public String menu() {
        return "Opções de livros\n" +
                "1. Cadastrar livros\n" +
                "Escolha uma das opções: \n";
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            System.out.println(menu());

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    addBook();
                    break;
                case 0:
                    break;
            }

        }

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


    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        DataAuthor author = null;
        System.out.println("Digite nome de um livro: ");
        String title = scanner.nextLine();

        try {
            Optional<Book> findBook = bookRepository.findByTitle(title.toLowerCase());

            if (findBook.isPresent()) {
                System.out.println("Já existe esse livro cadastrado!");
                return;
            }

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

                DataBook dataBook1 = new DataBook(dataBook.title().toLowerCase(), listAuthor, dataBook.listLanguages(), dataBook.downloadCount());
                Book book = new Book(dataBook1);
                bookRepository.save(book);
                System.out.println("Salvo com sucesso");
            }
        }catch(Exception e) {
            System.out.println("Já existe esse livro cadastrado");
        }
    }
}
