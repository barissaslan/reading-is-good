package com.barisaslan.readingisgood.helper;

import com.barisaslan.readingisgood.api.dto.RegisterCustomerRequest;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class TestHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static RegisterCustomerRequest getFakeRegisterCustomerRequest() {
        return new RegisterCustomerRequest("test@test.com", "1234");
    }

    public static Customer getFakeCustomer() {
        var customer = new Customer();
        customer.setEmail("baris@test.com");
        customer.setPassword("password");
        return customer;
    }

}
