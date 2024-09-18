package com.portfolio.libraryweb.models;

import jakarta.persistence.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String category;
    private String eventDate;

    @Column(columnDefinition = "varchar(3000)")
    private String text;

    public Event() {}

    public Event(String title, String category, String eventDate, String text) {
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String dateOfCreation) {
        this.eventDate = dateOfCreation;
    }
}
