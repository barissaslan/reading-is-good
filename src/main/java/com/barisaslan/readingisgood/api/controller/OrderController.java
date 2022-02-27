package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.api.dto.CreateOrderRequest;
import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/")
    public ResponseEntity<Void> createOrder(@RequestBody @Valid CreateOrderRequest request)
            throws OutOfStockException, BookNotFoundException, CustomerNotFoundException {
        orderService.createOrder(request.toModel());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
