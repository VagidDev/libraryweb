package com.portfolio.libraryweb.exceptions;
@Deprecated
public class EventAbsenceException extends Exception {
    public EventAbsenceException() {
        System.err.println("Event not found!");
    }

    public EventAbsenceException(long id) {
        System.err.println("Event with id '" + id + "' not found!");
    }
}
