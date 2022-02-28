package com.barisaslan.readingisgood.domain.service;

import com.barisaslan.readingisgood.dao.repository.OrderRepository;
import com.barisaslan.readingisgood.domain.dto.StatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    @Override
    public List<StatisticsDto> getMonthlyOrderStatistics() {
        return orderRepository.getOrderStatistics();
    }

}
