package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.domain.dto.OrderDto;
import com.barisaslan.readingisgood.domain.service.OrderService;
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
import static com.barisaslan.readingisgood.helper.TestHelper.getFakeCreateOrderRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void createOrderShouldReturnSuccess() throws Exception {
        mvc.perform(post("/api/orders/")
                        .content(asJsonString(getFakeCreateOrderRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        verify(orderService).createOrder(any(OrderDto.class));
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void getOrderShouldReturnSuccess() throws Exception {
        mvc.perform(get("/api/orders/12"))
                .andExpect(status().isOk()).andReturn();

        verify(orderService).getOrderById(anyString());
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void getOrdersByDateIntervalShouldReturnSuccess() throws Exception {
        mvc.perform(get("/api/orders/12"))
                .andExpect(status().isOk()).andReturn();

        verify(orderService).getOrderById(anyString());
        verifyNoMoreInteractions(orderService);
    }

}