package com.barisaslan.readingisgood.dao.repository;

import com.barisaslan.readingisgood.dao.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

}
