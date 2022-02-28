package com.barisaslan.readingisgood.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StatisticsDto {

    private String date;
    private long totalOrderCount;
    private long totalBookCount;
    private BigDecimal totalPurchasedAmount;

}
