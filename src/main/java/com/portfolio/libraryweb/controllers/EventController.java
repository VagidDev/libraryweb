package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.models.Event;
import com.portfolio.libraryweb.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/")
    public String getAllEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/events/add")
    public String getAddEvent() {
        return "add-event";
    }

    @PostMapping("/events/add")
    public ResponseEntity addEvent(@RequestBody Event event) {
        Event result = eventService.addEvent(event);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

}
