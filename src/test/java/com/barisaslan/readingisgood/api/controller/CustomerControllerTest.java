package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.domain.dto.CustomerDto;
import com.barisaslan.readingisgood.domain.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.barisaslan.readingisgood.helper.TestHelper.asJsonString;
import static com.barisaslan.readingisgood.helper.TestHelper.getFakeRegisterCustomerRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void registerShouldReturnSuccess() throws Exception {
        mvc.perform(post("/api/customers/register")
                        .content(asJsonString(getFakeRegisterCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        verify(customerService).createCustomer(any(CustomerDto.class));
        verifyNoMoreInteractions(customerService);
    }

}