package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.domain.dto.CustomerDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

    Customer findCustomerByEmail(String email) throws CustomerNotFoundException;

    void createCustomer(CustomerDto customerDto) throws EmailUserAlreadyExistException;

}
