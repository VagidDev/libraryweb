package com.portfolio.libraryweb.exceptions;


public class BookAbsenceException extends Exception {

    public BookAbsenceException() {
        System.err.println("Book not found!");
    }
    public BookAbsenceException(long id) {
        System.err.println("Book with id '" + id + "' not found!");
    }
}
