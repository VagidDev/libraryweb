package com.portfolio.libraryweb.services;

import com.portfolio.libraryweb.models.Event;
import com.portfolio.libraryweb.models.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        Iterable<Event> eventIterable = eventRepository.findAll();
        List<Event> events = new ArrayList<>();
        eventIterable.forEach(events::add);
        return events;
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

}
