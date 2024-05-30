package br.com.alura.literalura.livros.services;

public interface IConverterData {
    <T> T  getData(String json, Class<T> classe);
}
