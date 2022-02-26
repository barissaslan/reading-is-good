package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.api.dto.RegisterCustomerRequest;
import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.domain.service.CustomerService;
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
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterCustomerRequest registerRequest)
            throws EmailUserAlreadyExistException {

        customerService.createCustomer(registerRequest.getEmail(), registerRequest.getPassword());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
