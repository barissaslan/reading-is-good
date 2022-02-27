package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.repository.BookRepository;
import com.barisaslan.readingisgood.domain.dto.BookDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void addBookShouldSaveBook() {
        BookDto bookDto = BookDto.builder()
                .name("Hayvanlar Çiftliği")
                .stockCount(10)
                .build();

        bookService.addBook(bookDto);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBookStockShouldIncreaseStockCount() throws OutOfStockException, BookNotFoundException {
        Book book = new Book();
        book.setStockCount(10);
        book.setName("Book1");

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        var updateBookStockDto = UpdateBookStockDto.builder().bookId("1").stockChangeCount(3).build();
        bookService.updateBookStock(updateBookStockDto);

        assertEquals(13, book.getStockCount());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBookStockShouldDecreaseStockCount() throws OutOfStockException, BookNotFoundException {
        Book book = new Book();
        book.setStockCount(10);
        book.setName("Book1");

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));

        var updateBookStockDto = UpdateBookStockDto.builder().bookId("1").stockChangeCount(-3).build();
        bookService.updateBookStock(updateBookStockDto);

        assertEquals(7, book.getStockCount());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBookStockShouldThrowOutOfStockException() {
        Book book = new Book();
        book.setStockCount(2);
        book.setName("Book1");

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(book));
        var updateBookStockDto = UpdateBookStockDto.builder().bookId("1").stockChangeCount(-3).build();

        assertThrows(
                OutOfStockException.class,
                () -> bookService.updateBookStock(updateBookStockDto)
        );
    }

    @Test
    void updateBookStockShouldThrowBookNotFoundException() {
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());

        var updateBookStockDto = UpdateBookStockDto.builder().bookId("1").stockChangeCount(1).build();

        assertThrows(
                BookNotFoundException.class,
                () -> bookService.updateBookStock(updateBookStockDto)
        );
    }

}