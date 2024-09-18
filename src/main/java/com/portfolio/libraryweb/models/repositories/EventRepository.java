package com.portfolio.libraryweb.models.repositories;

import com.portfolio.libraryweb.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
