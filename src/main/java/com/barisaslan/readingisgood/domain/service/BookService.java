package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.domain.dto.BookDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;

public interface BookService {

    Book findBook(String id) throws BookNotFoundException;

    void addBook(BookDto bookDto);

    void updateBookStock(UpdateBookStockDto updateBookStockDto) throws BookNotFoundException, OutOfStockException;

}
