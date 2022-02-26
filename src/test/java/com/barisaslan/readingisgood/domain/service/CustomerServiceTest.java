package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import com.barisaslan.readingisgood.helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Test
    void createUserShouldReturnUser() throws EmailUserAlreadyExistException {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        customerService.createCustomer("test@test.com", "password");

        ArgumentCaptor<Customer> userCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(userCaptor.capture());
        verifyNoMoreInteractions(customerRepository);

        assertEquals("test@test.com", userCaptor.getValue().getEmail());
    }

    @Test
    void createUserShouldThrowEmailUserAlreadyExistException() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.of(TestHelper.getFakeCustomer()));

        EmailUserAlreadyExistException thrown = assertThrows(
                EmailUserAlreadyExistException.class,
                () -> customerService.createCustomer("test@test.com", "password")
        );

        assertTrue(thrown.getMessage().contains("exists"));
    }

    @Test
    void getCustomerByEmailShouldReturnCustomer() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.of(TestHelper.getFakeCustomer()));

        final UserDetails customer = customerService.loadUserByUsername("test");

        assertEquals("baris@test.com", customer.getUsername());
    }

    @Test
    void getCustomerByEmailShouldThrowException() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> customerService.loadUserByUsername("test")
        );

        assertTrue(thrown.getMessage().contains("test"));
    }
    
}