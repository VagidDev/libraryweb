package com.portfolio.libraryweb.services;

import com.portfolio.libraryweb.exceptions.BookAbsenceException;
import com.portfolio.libraryweb.models.Book;
import com.portfolio.libraryweb.models.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        Iterable<Book> bookIterable = bookRepository.findAll();
        List<Book> books = new ArrayList<>();
        bookIterable.forEach(books::add);
        return books;
    }

    public Book getBookById(long id) throws BookAbsenceException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new BookAbsenceException(id);
        }
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

}
