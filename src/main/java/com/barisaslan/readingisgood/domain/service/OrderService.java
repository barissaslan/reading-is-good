package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OrderNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.domain.dto.OrderDto;

import java.util.Date;
import java.util.List;

public interface OrderService {

    void createOrder(OrderDto orderDto) throws OutOfStockException, BookNotFoundException, CustomerNotFoundException;

    OrderDto getOrderById(String id) throws OrderNotFoundException, BookNotFoundException;

    List<OrderDto> getOrdersByDate(Date startDate, Date endDate) throws BookNotFoundException;

}
