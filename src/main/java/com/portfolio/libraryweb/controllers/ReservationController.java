package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.exceptions.BookAbsenceException;
import com.portfolio.libraryweb.models.Book;
import com.portfolio.libraryweb.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reservation/{book_id}")
    public ResponseEntity getBookReservation(@PathVariable long book_id) {
        return switch (reservationService.preserveBook(book_id)) {
            case ReservationService.SUCCESS -> ResponseEntity.status(HttpStatus.CREATED).build();
            case ReservationService.SAME_BOOK -> ResponseEntity.status(HttpStatus.CONFLICT).build();
            case ReservationService.FULL_STORAGE -> ResponseEntity.status(HttpStatus.LOCKED).build();
            case ReservationService.FAILURE -> ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        };
    }
//TODO:make reference better
    @DeleteMapping("/reservation/{book_id}/users/{username}")
    public ResponseEntity deleteBookReservation(@PathVariable long book_id, @PathVariable String username) {
        if(reservationService.returnBook(username, book_id))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
