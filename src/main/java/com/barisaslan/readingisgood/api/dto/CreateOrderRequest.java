package com.barisaslan.readingisgood.api.dto;


import com.barisaslan.readingisgood.dao.entity.Book;
import com.barisaslan.readingisgood.domain.dto.OrderDto;
import com.barisaslan.readingisgood.domain.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private List<OrderItemRequest> orderItems;

    @NotNull
    private String customerEmail;

    public OrderDto toModel() {
        return OrderDto.builder()
                .customerEmail(customerEmail)
                .orderItems(
                        orderItems.stream().map(i -> {
                            var book = new Book();
                            book.setId(i.getBookId());

                            return OrderItemDto.builder()
                                    .book(book)
                                    .count(i.getCount())
                                    .build();
                        }).collect(Collectors.toList())
                )
                .build();
    }

}
