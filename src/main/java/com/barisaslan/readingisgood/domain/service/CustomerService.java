package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

    Customer createCustomer(String email, String password) throws EmailUserAlreadyExistException;

}
