package com.barisaslan.readingisgood.integration;

import com.barisaslan.readingisgood.api.controller.OrderController;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.dao.entity.OrderStatus;
import com.barisaslan.readingisgood.dao.repository.BookRepository;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import com.barisaslan.readingisgood.dao.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.barisaslan.readingisgood.helper.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class OrderIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .build();
    }

    @AfterEach
    void cleanUpEach() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void createOrderShouldSaveOrder() throws Exception {
        Book book = getFakeBook1();
        bookRepository.save(book);

        Customer customer = getFakeCustomer();
        customerRepository.save(customer);

        mvc.perform(post("/api/orders/")
                        .content(objectToJsonString(getFakeCreateOrderRequest(book.getId())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        List<Order> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
    }

    @Test
    public void getOrderShouldReturnOrder() throws Exception {
        Book book = new Book();
        book.setStockCount(5);
        book.setPrice(BigDecimal.TEN);
        book.setTitle("1984");
        bookRepository.save(book);

        Customer customer = new Customer();
        customer.setEmail("test@test.com");
        customer.setPassword("123");
        customerRepository.save(customer);

        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalPrice(BigDecimal.TEN);
        order.setCreatedDate(new Date());
        order.setStatus(OrderStatus.CREATED);

        HashMap<String, Long> orderItems = new HashMap<>();
        orderItems.put(book.getId(), 2L);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        mvc.perform(get(String.format("/api/orders/%s/", order.getId())))
                .andExpect(status().isOk()).andReturn();

        List<Order> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
        assertEquals(BigDecimal.TEN, orders.get(0).getTotalPrice());
    }

}
