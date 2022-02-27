package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.common.exceptions.OrderNotFoundException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.domain.dto.CustomerDto;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CustomerService extends UserDetailsService {

    Customer findCustomerByEmail(String email) throws CustomerNotFoundException;

    void createCustomer(CustomerDto customerDto) throws EmailUserAlreadyExistException;

    List<OrderDto> getOrders(String email) throws CustomerNotFoundException, OrderNotFoundException, BookNotFoundException;

}
