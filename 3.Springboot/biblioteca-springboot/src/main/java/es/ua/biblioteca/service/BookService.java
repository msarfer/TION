package es.ua.biblioteca.service;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements BookServiceInterface{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return  bookRepository.findById(id);
    }

    @Override
    public String create(Book book) {
        Book b = bookRepository.save(book);
        return "book created: " + b;
    }

    @Override
    public String update(Book book) {
        if(book.getId() != 0) {
            Book b = bookRepository.save(book);
            return "book updated: " + b;
        }
        return "No id provided for the book";
    }

    @Override
    public String delete(long id) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(value -> bookRepository.delete(value));
        return "book deleted";
    }

    @Override
    public List<Book> search(String title) {
        return bookRepository.findByTitle(title);
    }
}
