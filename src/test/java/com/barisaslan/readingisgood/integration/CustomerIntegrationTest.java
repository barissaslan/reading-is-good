package com.barisaslan.readingisgood.integration;

import com.barisaslan.readingisgood.api.controller.CustomerController;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import com.barisaslan.readingisgood.dao.repository.OrderRepository;
import com.barisaslan.readingisgood.domain.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.barisaslan.readingisgood.helper.TestHelper.getFakeRegisterCustomerRequest;
import static com.barisaslan.readingisgood.helper.TestHelper.objectToJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
    }

    @AfterEach
    void cleanUpEach() {
        customerRepository.deleteAll();
    }

    @Test
    void registerShouldSaveCustomer() throws Exception {
        mvc.perform(post("/api/customers/register/")
                        .content(objectToJsonString(getFakeRegisterCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        List<Customer> customerList = customerRepository.findAll();
        assertEquals(1, customerList.size());
    }

    @Test
    void registerShouldDenySameEmail() throws Exception {
        mvc.perform(post("/api/customers/register/")
                        .content(objectToJsonString(getFakeRegisterCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        mvc.perform(post("/api/customers/register/")
                        .content(objectToJsonString(getFakeRegisterCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable()).andReturn();

        List<Customer> customerList = customerRepository.findAll();
        assertEquals(1, customerList.size());
    }

}
