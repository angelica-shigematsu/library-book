package br.com.alura.literalura.livros.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
                       @JsonAlias("title") String title,
                       @JsonAlias("authors") List<DataAuthor> authors,
                       @JsonAlias("languages") List<String> listLanguages,
                       @JsonAlias("download_count") int downloadCount) {
}
