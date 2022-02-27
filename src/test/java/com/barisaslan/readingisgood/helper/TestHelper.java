package com.barisaslan.readingisgood.helper;

import com.barisaslan.readingisgood.api.dto.*;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.domain.dto.CustomerDto;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import com.barisaslan.readingisgood.domain.dto.OrderItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static AddBookRequest getFakeAddBookRequest() {
        return new AddBookRequest("1984", 5, BigDecimal.TEN);
    }

    public static UpdateBookStockRequest getFakeUpdateBookStockRequest() {
        return new UpdateBookStockRequest("1", -1);
    }

    public static CreateOrderRequest getFakeCreateOrderRequest() {
        var request = new CreateOrderRequest();
        request.setCustomerEmail("baris@test");

        List<OrderItemRequest> orderItemRequestList = new ArrayList<>();
        orderItemRequestList.add(new OrderItemRequest("1", 5));
        orderItemRequestList.add(new OrderItemRequest("2", 100));

        request.setOrderItems(orderItemRequestList);

        return request;
    }

    public static Customer getFakeCustomer() {
        var customer = new Customer();
        customer.setId("1");
        customer.setEmail("baris@test.com");
        customer.setPassword("password");
        return customer;
    }

    public static CustomerDto getFakeCustomerDto() {
        return CustomerDto.builder()
                .email("baris@test.com")
                .password("password")
                .build();
    }

    public static Book getFakeBook1() {
        Book book1 = new Book();
        book1.setId("1");
        book1.setTitle("Book1");
        book1.setPrice(BigDecimal.valueOf(10.5));
        book1.setStockCount(100);
        return book1;
    }

    public static Book getFakeBook2() {
        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("Book2");
        book2.setPrice(BigDecimal.valueOf(20));
        book2.setStockCount(100);
        return book2;
    }

    public static OrderDto getFakeOrderDto() {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();

        orderItemDtoList.add(OrderItemDto.builder()
                .book(getFakeBook1())
                .count(3)
                .build());

        orderItemDtoList.add(OrderItemDto.builder()
                .book(getFakeBook2())
                .count(2)
                .build());

        return OrderDto.builder()
                .customerEmail("baris@test.com")
                .orderItems(orderItemDtoList)
                .build();
    }

    public static Order getFakeOrder() {
        Order order = new Order();
        order.setId("1");
        order.setCustomer(getFakeCustomer());
        order.setTotalPrice(BigDecimal.TEN);

        HashMap<String, Long> orderItems = new HashMap<>();
        orderItems.put("1", 5L);
        orderItems.put("2", 10L);
        order.setOrderItems(orderItems);

        return order;
    }

}
