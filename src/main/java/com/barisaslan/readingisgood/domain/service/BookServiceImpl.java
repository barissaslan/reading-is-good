package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.repository.BookRepository;
import com.barisaslan.readingisgood.domain.dto.BookDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book findBook(String id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void addBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setStockCount(bookDto.getStockCount());
        book.setPrice(bookDto.getPrice());
        bookRepository.save(book);
        log.debug("Book added: {}", book);
    }

    @Override
    public void updateBookStock(UpdateBookStockDto dto) throws BookNotFoundException, OutOfStockException {
        Book book = findBook(dto.getBookId());

        if (book.getStockCount() + dto.getStockChangeCount() < 0) {
            throw new OutOfStockException();
        }

        book.setStockCount(book.getStockCount() + dto.getStockChangeCount());
        bookRepository.save(book);
        log.debug("Book stock updated. Stock change count: {}, updated book: {}", dto.getStockChangeCount(), book);
    }

}
