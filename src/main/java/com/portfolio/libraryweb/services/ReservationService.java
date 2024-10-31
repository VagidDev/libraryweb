package com.portfolio.libraryweb.services;

import com.portfolio.libraryweb.exceptions.BookAbsenceException;
import com.portfolio.libraryweb.models.Book;
import com.portfolio.libraryweb.models.Reservation;
import com.portfolio.libraryweb.models.User;
import com.portfolio.libraryweb.models.repositories.BookRepository;
import com.portfolio.libraryweb.models.repositories.ReservationRepository;
import com.portfolio.libraryweb.models.repositories.UserRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {

    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;
    public static final int SAME_BOOK = 1;
    public static final int FULL_STORAGE = 2;
    public static final int RENTED_BOOK = 3;

    private final String sender;

    private final String senderPassword;
    private final String host;

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository,
                              @Value("${sender.address}") String sender, @Value("${sender.password}") String senderPassword,
                              @Value("${host}")String host) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.sender = sender;
        this.senderPassword = senderPassword;
        this.host = host;
    }

    private int registerBook(Optional<User> optionalUser, Optional<Book> optionalBook) {
        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book book = optionalBook.get();
            User user = optionalUser.get();
            if (user.getReservations().size() > 2) {
                return FULL_STORAGE;
            }
            if (user.getReservations().stream().anyMatch(reservation -> reservation.getBook().equals(book))) {
                return SAME_BOOK;
            }
            if (!book.isFree()) {
                return RENTED_BOOK;
            }
            Reservation reservation = new Reservation(optionalUser.get(), optionalBook.get(), new Date());
            reservationRepository.save(reservation);
            return SUCCESS;
        }
        return FAILURE;
    }

    public int preserveBook(long book_id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Book> optionalBook = bookRepository.findById(book_id);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return registerBook(optionalUser, optionalBook);
    }

    public int preserveBookToUser(String username, long book_id) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Book> optionalBook = bookRepository.findById(book_id);
        return registerBook(optionalUser, optionalBook);
    }

    public List<Reservation> getReservationsOfCurrentUser() {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent()) {
            return user.get().getReservations();
        } else
            //TODO: create new exception that will say about user not found
            throw new RuntimeException("User not found");
    }

    public List<Reservation> getReservationsByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getReservations();
        } else
            throw new RuntimeException("User not found");
    }

    public Reservation getReservationByBookId(long bookId) throws BookAbsenceException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get().getReservation();
        } else {
            throw new BookAbsenceException(bookId);
        }
    }

    public List<Reservation> getAllReservations() {
        Iterable<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> reservationList = new ArrayList<>();
        reservations.forEach(reservationList::add);
        return reservationList;
    }

    public boolean returnBook(String username, long book_id) {
        Reservation deletedReservation = null;
        for (Reservation reservation : reservationRepository.findAll()) {
            if (reservation.getBook().getId() == book_id && reservation.getUser().getUsername().equals(username)) {
                deletedReservation = reservation;
            }
        }
        if (deletedReservation != null) {
            reservationRepository.delete(deletedReservation);
            return true;
        }
        return false;
    }

    //TODO: Add logging
    public void sendNotificationToUser(long bookId) {
        try {
            Reservation reservation = getReservationByBookId(bookId);
            User user = reservation.getUser();

            String messageTitle = "Термин аренды книги закончился!";

            String messageText = "Дорогой читатель, " + reservation.getSimpleDate() +
                    " Вы взяли в аренду книгу " + reservation.getBook().getTitle() + ", " +
                    reservation.getBook().getAuthor() + ". Просим вернуть ее в библиотеку в ближайшее время. " +
                    "С уважением, Администрация библиотеки села Семеновка!";

            Properties props = System.getProperties();

            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, senderPassword);
                }
            });

            //send a message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            message.setSubject(messageTitle);

            message.setText(messageText);

            Transport.send(message);
            //here will be logging
        } catch (BookAbsenceException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
