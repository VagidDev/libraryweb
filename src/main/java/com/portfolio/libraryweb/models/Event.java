package com.portfolio.libraryweb.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String category;
    private Date eventDate;

    @Column(columnDefinition = "varchar(3000)")
    private String text;

    public Event() {}

    public Event(String title, String category, Date eventDate, String text) {
        this.title = title;
        this.category = category;
        this.eventDate = eventDate;
        this.text = text;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date dateOfCreation) {
        this.eventDate = dateOfCreation;
    }

    public String getFormattedEventDate() {
        return String.format("Дата проведения: %1$td.%1$tm.%1$tY", eventDate);
    }
}
