package br.com.alura.literalura.livros.web;


import br.com.alura.literalura.livros.models.*;
import br.com.alura.literalura.livros.repositories.AuthorRepository;
import br.com.alura.literalura.livros.repositories.BookRepository;
import br.com.alura.literalura.livros.services.ApiConsumption;
import br.com.alura.literalura.livros.services.ConveterData;

import java.util.*;
import java.util.stream.Collectors;

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
                "2. Listar todos os livros\n" +
                "3. Listar por autores\n" +
                "4. Listar por idiomas\n" +
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
                case 2:
                    listBook();
                    break;
                case 3:
                    System.out.println(listByAuthor());
                    break;
                case 4:
                    listByLanguage();
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

            System.out.println(listAuthor.get(0).name());

            DataBook dataBook1 = new DataBook(dataBook.title().toLowerCase(), listAuthor, dataBook.languages(), dataBook.downloadCount());
            Book book = new Book(dataBook1);
            System.out.println(dataBook1);
            bookRepository.save(book);
            System.out.println("Salvo com sucesso");
            }
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    private void listBook() {
        for(Book book: listBooks()) {
            System.out.println(book.toString());
        }
    }

    private String listByAuthor() {
        System.out.println("Digite o nome do autor");
        String author = scanner2.nextLine().toLowerCase();

        Optional<Author> listAuthor = authorRepository.findByNameContainingIgnoreCase(author);

        if (listAuthor.isPresent()) {
           return listAuthor.toString();
        }
        return "Não foi possível encontrar esse autor";
    }

    private void listByLanguage() {
        System.out.println("Digite o idioma: ");
        String language = scanner2.nextLine();
        Optional<List<Book>> listBookByLanguage = bookRepository.findByLanguagesContainingIgnoreCase(language);

        listBookByLanguage.stream().sorted().forEach(System.out::println);

    }
}
