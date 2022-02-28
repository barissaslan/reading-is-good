package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.domain.dto.StatisticsDto;

import java.util.List;

public interface StatisticsService {

    List<StatisticsDto> getMonthlyOrderStatistics();

}
