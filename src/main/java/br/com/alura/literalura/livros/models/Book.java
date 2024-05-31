package br.com.alura.literalura.livros.models;

import jakarta.persistence.*;
import org.springframework.data.auditing.AuditingHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;
    @Column(unique = true)
    private String title;

    private List<String> listLanguages;
    private int downloadCount;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    public Book() {}
    public Book(DataBook databook) {
        List<Author> listAuthor = new ArrayList<>();
        for (DataAuthor author: databook.authors()) {
            DataAuthor dataAuthor = new DataAuthor(author.name(), author.birthYear(), author.deathYear());
            listAuthor.add(new Author(dataAuthor));
        }
        List<String> listLanguages = new ArrayList<>(databook.listLanguages());
        this.listLanguages = listLanguages;
        this.authors = listAuthor;
        this.title = databook.title();
        this.downloadCount = databook.downloadCount();

    }

    private List<Author> authors() {
        return authors;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public List<String> getListLanguages() {
        return listLanguages;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", listLanguages=" + listLanguages +
                ", downloadCount=" + downloadCount +
                ", authors=" + authors +
                '}';
    }
}
