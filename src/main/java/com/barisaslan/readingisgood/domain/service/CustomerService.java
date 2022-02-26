package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerService {

    Customer getCustomerByEmail(String email) throws UsernameNotFoundException;

    Customer createCustomer(String email, String password) throws EmailUserAlreadyExistException;

}
