package com.portfolio.libraryweb.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String genre;
    private String author;
    private String dateOfWriting;
    @Column(columnDefinition = "varchar(20000)")
    private String preface;
    @Column(columnDefinition = "longtext")
    private String text;
    @OneToOne(mappedBy = "book")
    private Reservation reservation;

    public Book() {}

    public Book(String title, String preface, String genre, String author, String text, String dateOfWriting) {
        this.title = title;
        this.preface = preface;
        this.genre = genre;
        this.author = author;
        this.text = text;
        this.dateOfWriting = dateOfWriting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateOfWriting() {
        return dateOfWriting;
    }

    public void setDateOfWriting(String dateOfWriting) {
        this.dateOfWriting = dateOfWriting;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isFree() {
        return reservation == null;
    }

}
