package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.repository.BookRepository;
import com.barisaslan.readingisgood.domain.dto.BookDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public void addBook(BookDto bookDto) {
        Book book = new Book();
        book.setName(book.getName());
        book.setStockCount(book.getStockCount());
        bookRepository.save(book);
    }

    @Override
    public void updateBookStock(UpdateBookStockDto dto) throws BookNotFoundException, OutOfStockException {
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(BookNotFoundException::new);

        if (book.getStockCount() + dto.getStockChangeCount() < 0) {
            throw new OutOfStockException();
        }

        book.setStockCount(book.getStockCount() + dto.getStockChangeCount());
        bookRepository.save(book);
    }

}
