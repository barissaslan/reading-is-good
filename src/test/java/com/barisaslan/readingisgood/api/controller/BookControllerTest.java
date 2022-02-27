package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.domain.dto.BookDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import com.barisaslan.readingisgood.domain.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.barisaslan.readingisgood.helper.TestHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void addBookShouldReturnSuccess() throws Exception {
        mvc.perform(post("/api/books/")
                        .content(asJsonString(getFakeAddBookRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        verify(bookService).addBook(any(BookDto.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void updateBookStockShouldReturnSuccess() throws Exception {
        mvc.perform(patch("/api/books/")
                        .content(asJsonString(getFakeUpdateBookStockRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        verify(bookService).updateBookStock(any(UpdateBookStockDto.class));
        verifyNoMoreInteractions(bookService);
    }

}