package com.barisaslan.readingisgood.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDto {

    private String id;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalPrice;

    @JsonIgnore
    private String customerEmail;

}
