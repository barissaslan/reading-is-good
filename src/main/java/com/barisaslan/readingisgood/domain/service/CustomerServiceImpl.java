package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Customer createCustomer(String email, String password) throws EmailUserAlreadyExistException {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if (customer.isPresent()) {
            throw new EmailUserAlreadyExistException();
        }

        var newCustomer = new Customer();
        newCustomer.setEmail(email);
        newCustomer.setPassword(bCryptPasswordEncoder.encode(password));
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);

        if (customer.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return customer.get();
    }

}
