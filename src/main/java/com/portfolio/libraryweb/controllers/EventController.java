package com.portfolio.libraryweb.controllers;

import com.portfolio.libraryweb.models.Event;
import com.portfolio.libraryweb.models.repositories.UserRepository;
import com.portfolio.libraryweb.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EventController {
    private final EventService service;

    public EventController(EventService eventService) {
        service = eventService;
    }

    @GetMapping("/events/")
    public String getAllEvents(Model model) {
        List<Event> events = service.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    //don't work now
    /*@PostMapping("/add_event/")
    public String addEvent(@PathVariable long hashID, @RequestParam String title, @RequestParam String category,
                           @RequestParam String eventDate, @RequestParam String text) {
        Event event = new Event(title, category, eventDate, text);
        service.addEvent(event);
        return "redirect:/";
    }
    //test method that receives json object
//    @PostMapping("/add_event/")
    public String addEvent(Event event) {
        service.addEvent(event);
        return "Your event was added!";
    }*/

}
