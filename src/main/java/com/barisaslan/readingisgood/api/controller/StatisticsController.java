package com.barisaslan.readingisgood.api.controller;

import com.barisaslan.readingisgood.domain.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping(value = "/")
    public ResponseEntity<Void> getMonthlyOrderStatistics() {
        statisticsService.getMonthlyOrderStatistics();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
