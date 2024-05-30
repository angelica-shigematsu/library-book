package br.com.alura.literalura.livros.models;

public enum Genre {
    HISTORICALFICTION("historical Fiction"),
    CHILDREN("Children's Literature"),

    BESTBOOKS("Best Books Ever Listings"),

    ROMANCE("romance");

    private String name;

    Genre(String name) {
        this.name = name;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.name.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
