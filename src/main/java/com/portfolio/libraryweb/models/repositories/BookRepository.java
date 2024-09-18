package com.portfolio.libraryweb.models.repositories;

import com.portfolio.libraryweb.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
