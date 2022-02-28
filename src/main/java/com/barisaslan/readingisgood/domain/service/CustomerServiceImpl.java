package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.common.exceptions.OrderNotFoundException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import com.barisaslan.readingisgood.domain.dto.CustomerDto;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderService orderService;

    @Override
    public Customer findCustomerByEmail(String email) throws CustomerNotFoundException {
        return customerRepository.findCustomerByEmail(email).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public void createCustomer(CustomerDto customerDto) throws EmailUserAlreadyExistException {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(customerDto.getEmail());
        if (customer.isPresent()) {
            log.error("EmailUserAlreadyExistException! Email: {}", customerDto.getEmail());
            throw new EmailUserAlreadyExistException();
        }

        var newCustomer = new Customer();
        newCustomer.setEmail(customerDto.getEmail());
        newCustomer.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
        customerRepository.save(newCustomer);
        log.debug("Customer created: {}", newCustomer);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);

        if (customer.isEmpty()) {
            log.error("UsernameNotFoundException. Email: {}", email);
            throw new UsernameNotFoundException(email);
        }

        return customer.get();
    }

    @Override
    public List<OrderDto> getOrders(String email) throws CustomerNotFoundException, OrderNotFoundException, BookNotFoundException {
        Customer customer = findCustomerByEmail(email);
        List<Order> orders = customer.getOrders();

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (var order : orders) {
            OrderDto orderDto = orderService.getOrderById(order.getId());
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }


}
