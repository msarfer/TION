package es.ua.biblioteca.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.ua.biblioteca.service.WikidataService;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.service.BookServiceInterface;

@Controller
public class ControllerTM {
    @Autowired
    private BookServiceInterface bookService;
    @Autowired
    private WikidataService wikidataService;

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

    @RequestMapping("/searchAuthor")
    public String searchAuthor(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "limit", required = false) Integer limit, Model model) {

        if (name != null) {
            List<Map<String, String>> res = wikidataService.getAuthors(limit, name);
            model.addAttribute("autores", res);
        } else {
            model.addAttribute("autores", new ArrayList<>());
        }


        return "searchAuthor";
    }

    @PostMapping("/createBook")
    public String createBook(@ModelAttribute Book book, Model model) {
        String result = bookService.create(book);
        model.addAttribute("book", book);
        model.addAttribute("result", result);
        return "result";
    }
}
