package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.common.exceptions.BookNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.CustomerNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OrderNotFoundException;
import com.barisaslan.readingisgood.common.exceptions.OutOfStockException;
import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.dao.entity.Customer;
import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.dao.entity.OrderStatus;
import com.barisaslan.readingisgood.dao.repository.CustomerRepository;
import com.barisaslan.readingisgood.dao.repository.OrderRepository;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import com.barisaslan.readingisgood.domain.dto.OrderItemDto;
import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final BookService bookService;
    private final CustomerRepository customerRepository;
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

        Customer customer = customerRepository.findCustomerByEmail(orderDto.getCustomerEmail())
                .orElseThrow(CustomerNotFoundException::new);

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

    @Override
    public OrderDto getOrderById(String id) throws OrderNotFoundException, BookNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);

        List<OrderItemDto> orderItemDtoList = prepareOrderItemDtoList(order);

        return OrderDto.builder()
                .id(order.getId())
                .customerEmail(order.getCustomer().getEmail())
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItemDtoList)
                .build();
    }

    private List<OrderItemDto> prepareOrderItemDtoList(Order order) throws BookNotFoundException {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : order.getOrderItems().entrySet()) {
            String bookId = entry.getKey();
            Long stockCount = entry.getValue();

            Book book = bookService.findBook(bookId);
            OrderItemDto orderItemDto = OrderItemDto.builder().count(stockCount).book(book).build();
            orderItemDtoList.add(orderItemDto);
        }
        return orderItemDtoList;
    }

    @Override
    public List<OrderDto> getOrdersByDate(Date startDate, Date endDate) throws BookNotFoundException {
        List<Order> orders = orderRepository.findAllByCreatedDateBetween(startDate, endDate);

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (var order : orders) {
            orderDtoList.add(OrderDto.builder()
                    .id(order.getId())
                    .customerEmail(order.getCustomer().getEmail())
                    .totalPrice(order.getTotalPrice())
                    .orderItems(prepareOrderItemDtoList(order))
                    .build());
        }

        return orderDtoList;
    }

}
