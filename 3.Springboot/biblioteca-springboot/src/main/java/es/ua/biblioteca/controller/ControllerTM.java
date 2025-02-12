package es.ua.biblioteca.controller;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.service.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ControllerTM {
    @Autowired
    private BookServiceInterface bookService;

    @RequestMapping("/books")
    public String books(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "biblioteca";
    }
    @RequestMapping("/")
    public String hola(Model modelo) {
        return "index";
    }

    @RequestMapping("/createBook")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "form";
    }

    @RequestMapping("/searchBook")
    public String searchBook(@RequestParam(value = "text", required = false) String text, Model model) {

        model.addAttribute("libros", bookService.search(text));

        return "searchForm";
    }

    @PostMapping("/createBook")
    public String createBook(@ModelAttribute Book book, Model model) {
        String result = bookService.create(book);
        model.addAttribute("book", book);
        model.addAttribute("result", result);
        return "result";
    }
}
