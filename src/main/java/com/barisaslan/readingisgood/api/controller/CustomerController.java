package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.api.dto.RegisterCustomerRequest;
import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.common.exceptions.OrderNotFoundException;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import com.barisaslan.readingisgood.domain.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterCustomerRequest request)
            throws EmailUserAlreadyExistException {

        customerService.createCustomer(request.toModel());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{email}/orders/")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable String email)
            throws CustomerNotFoundException, OrderNotFoundException, BookNotFoundException {

        List<OrderDto> customerDtoList = customerService.getOrders(email);

        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

}
