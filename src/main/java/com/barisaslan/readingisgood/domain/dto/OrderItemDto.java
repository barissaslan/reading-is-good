package com.barisaslan.readingisgood.domain.dto;

import com.barisaslan.readingisgood.dao.entity.Book;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {

    private Book book;
    private long count;

    public BigDecimal totalPrice() {
        return book.getPrice().multiply(BigDecimal.valueOf(count));
    }

}
