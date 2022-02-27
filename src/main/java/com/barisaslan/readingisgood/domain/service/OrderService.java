package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.domain.dto.OrderDto;

public interface OrderService {

    void createOrder(OrderDto orderDto) throws OutOfStockException, BookNotFoundException, CustomerNotFoundException;

}
