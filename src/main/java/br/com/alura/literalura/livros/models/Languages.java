package br.com.alura.literalura.livros.models;

public enum Languages {
    EN("en"),
    PT("pt"),
    FR("fr"),
    ES("es");


    private String languages;

    Languages(String languages) {
        this.languages = languages;
    }


    public static Languages fromString(String text) {
        for (Languages languages : Languages.values()) {
            if (languages.languages.equalsIgnoreCase(text)) {
                return languages;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para a string fornecida: " + text);
    }
}
