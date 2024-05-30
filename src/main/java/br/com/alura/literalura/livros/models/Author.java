package br.com.alura.literalura.livros.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    private String name;

    private int birthYear;

    private int deathYear;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author(){}

    public Author(DataAuthor author) {
        this.name = author.name();
        this.birthYear = author.birthYear();
        this.deathYear = author.deathYear();
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", books=" + books +
                '}';
    }
}
