package es.ua.biblioteca.service;

import es.ua.biblioteca.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {
    List<Book> getAllBooks();
    Optional<Book> getBookById(long id);
    String create(Book book);
    String update(Book book);
    String delete(long id);
    List<Book> search(String title);
}
