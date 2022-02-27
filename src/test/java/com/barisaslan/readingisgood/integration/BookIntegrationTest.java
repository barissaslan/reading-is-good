package com.barisaslan.readingisgood.integration;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
    }

    @Test
    public void shouldDenyConcurrentStockUpdates() throws BookNotFoundException {
        final Book book = new Book();
        book.setTitle("Book1");
        book.setStockCount(5);
        bookRepository.save(book);

        book.setStockCount(book.getStockCount() - 1);

        Book sameBook = bookRepository
                .findById(book.getId())
                .orElseThrow(BookNotFoundException::new);

        sameBook.setStockCount(sameBook.getStockCount() - 1);

        assertThrows(
                OptimisticLockingFailureException.class,
                () -> {
                    bookRepository.save(book);
                    bookRepository.save(sameBook);
                }
        );
    }

}
