package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.exceptions.BookAbsenceException;
import com.portfolio.libraryweb.models.Book;
import com.portfolio.libraryweb.services.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable long id, Model model) {
        try {
            model.addAttribute("book", bookService.getBookById(id));
            return "read-book";
        } catch (BookAbsenceException e) {
            return "redirect:/books/";
        }
    }

    @PostMapping(value = "/books/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "redirect:/books/";
    }

    @GetMapping("/books/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addBookForm() {
        return "add-book";
    }

}
