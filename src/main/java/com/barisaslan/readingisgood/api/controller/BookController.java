package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.api.dto.AddBookRequest;
import com.barisaslan.readingisgood.api.dto.UpdateBookStockRequest;
import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/")
    public ResponseEntity<Void> addBook(@RequestBody @Valid AddBookRequest request) {
        bookService.addBook(request.toModel());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/")
    public ResponseEntity<Void> updateBookStock(@RequestBody @Valid UpdateBookStockRequest request)
            throws OutOfStockException, BookNotFoundException {
        bookService.updateBookStock(request.toModel());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
