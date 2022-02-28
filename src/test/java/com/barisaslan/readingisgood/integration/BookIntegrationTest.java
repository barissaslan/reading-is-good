package com.barisaslan.readingisgood.integration;

import com.barisaslan.readingisgood.api.controller.BookController;
import com.barisaslan.readingisgood.api.dto.UpdateBookStockRequest;
import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.repository.BookRepository;
import com.barisaslan.readingisgood.domain.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static com.barisaslan.readingisgood.helper.TestHelper.getFakeAddBookRequest;
import static com.barisaslan.readingisgood.helper.TestHelper.objectToJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private BookController bookController;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .build();
    }

    @AfterEach
    void cleanUpEach() {
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

    @Test
    public void addBookShouldSaveBook() throws Exception {
        mvc.perform(post("/api/books/")
                        .content(objectToJsonString(getFakeAddBookRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        List<Book> bookList = bookRepository.findAll();
        assertEquals(1, bookList.size());
    }

    @Test
    public void updateBookStockShouldReduceStockCount() throws Exception {
        Book book = new Book();
        book.setStockCount(5);
        book.setPrice(BigDecimal.TEN);
        book.setTitle("1984");

        bookRepository.save(book);

        mvc.perform(patch("/api/books/")
                        .content(objectToJsonString(new UpdateBookStockRequest(book.getId(), -2)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        book = bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new);
        assertEquals(3, book.getStockCount());
    }

}
