package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.exceptions.BookAbsenceException;
import com.portfolio.libraryweb.models.Book;
import com.portfolio.libraryweb.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books/")
    public String getBooks(Model model) {
        List<Book> books = service.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable long id, Model model) {
        try {
            model.addAttribute("book", service.getBookById(id));
            return "read-book";
        } catch (BookAbsenceException e) {
            return "redirect:/books/";
        }
    }
    //TODO: rework the method so that it receives a json object
    @PostMapping(value = "/books/add")
    /*public String addBook(@RequestParam String title, @RequestParam String preface, @RequestParam String genre,
                          @RequestParam String author, @RequestParam String dateOfWriting, @RequestParam String text) {
        Book book = new Book(title, preface, genre, author, text, dateOfWriting);
        service.addBook(book);
        return "redirect:/books/";
    }*/
    //idk for what it
    @GetMapping("/books/add/")
    public String addBookForm(Model model) {
        return "add-book";
    }

}
