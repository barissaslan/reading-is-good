package com.barisaslan.readingisgood.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookDto {

    private String id;
    private String title;
    private long stockCount;
    private BigDecimal price;

}
