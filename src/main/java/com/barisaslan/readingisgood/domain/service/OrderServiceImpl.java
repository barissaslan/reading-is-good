package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.dao.entity.OrderStatus;
import com.barisaslan.readingisgood.dao.repository.OrderRepository;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import com.barisaslan.readingisgood.domain.dto.OrderItemDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final BookService bookService;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;

    @Override
    public void createOrder(OrderDto orderDto) throws OutOfStockException, BookNotFoundException, CustomerNotFoundException {
        Order order = new Order();

        HashMap<String, Long> orderItems = prepareOrderItems(orderDto);
        order.setOrderItems(orderItems);

        BigDecimal totalPrice = getTotalPrice(orderDto);
        order.setTotalPrice(totalPrice);
        order.setCreatedDate(new Date());
        order.setStatus(OrderStatus.CREATED);

        Customer customer = customerService.findCustomerByEmail(orderDto.getCustomerEmail());
        order.setCustomer(customer);

        orderRepository.save(order);
    }

    private HashMap<String, Long> prepareOrderItems(OrderDto orderDto) throws BookNotFoundException, OutOfStockException {
        HashMap<String, Long> orderItems = new HashMap<>();
        for (OrderItemDto item : orderDto.getOrderItems()) {
            orderItems.put(item.getBook().getId(), item.getCount());
            loadBookRecord(item);
            updateStockCount(item);
        }
        return orderItems;
    }

    private void loadBookRecord(OrderItemDto item) throws BookNotFoundException {
        item.setBook(bookService.findBook(item.getBook().getId()));
    }

    private void updateStockCount(OrderItemDto item) throws BookNotFoundException, OutOfStockException {
        bookService.updateBookStock(UpdateBookStockDto.builder()
                .bookId(item.getBook().getId())
                .stockChangeCount(-item.getCount())
                .build());
    }

    private BigDecimal getTotalPrice(OrderDto orderDto) {
        return orderDto.getOrderItems()
                .stream()
                .map(OrderItemDto::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
