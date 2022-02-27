package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OrderNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import com.barisaslan.readingisgood.dao.repository.OrderRepository;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.barisaslan.readingisgood.helper.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookService bookService;

    @Test
    void createOrderShouldSaveOrder() throws OutOfStockException, BookNotFoundException, CustomerNotFoundException {
        when(bookService.findBook("1")).thenReturn(getFakeBook1());
        when(bookService.findBook("2")).thenReturn(getFakeBook2());
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.of(new Customer()));

        orderService.createOrder(getFakeOrderDto());

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        assertEquals(2, captor.getValue().getOrderItems().size());
        assertEquals(BigDecimal.valueOf(71.5), captor.getValue().getTotalPrice());
    }

    @Test
    void getOrderByIdShouldReturnOrderDto() throws BookNotFoundException, OrderNotFoundException {
        when(bookService.findBook("1")).thenReturn(getFakeBook1());
        when(bookService.findBook("2")).thenReturn(getFakeBook2());
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(getFakeOrder()));

        OrderDto orderDto = orderService.getOrderById(anyString());

        assertEquals(2, orderDto.getOrderItems().size());
    }

    @Test
    void getOrdersByDateShouldReturnOrderDtoList() throws BookNotFoundException {
        when(bookService.findBook("1")).thenReturn(getFakeBook1());
        when(bookService.findBook("2")).thenReturn(getFakeBook2());
        when(orderRepository.findAllByCreatedDateBetween(any(Date.class), any(Date.class)))
                .thenReturn(List.of(getFakeOrder()));

        List<OrderDto> orderDtoList = orderService.getOrdersByDate(new Date(), new Date());

        assertEquals(1, orderDtoList.size());
    }

}