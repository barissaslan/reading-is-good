package com.barisaslan.readingisgood.dao.repository;

import com.barisaslan.readingisgood.dao.entity.Order;
import com.barisaslan.readingisgood.domain.dto.StatisticsDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByCreatedDateBetween(Date startDate, Date endDate);

    @Query("select CONCAT(toString(YEAR(createdDate)), '-', toString(MONTH(createdDate))) as date, count(*) as totalOrderCount, sum(totalPrice) as totalPurchasedAmount " +
            "from order " +
            "group by CONCAT(toString(YEAR(createdDate)), '-', toString(MONTH(createdDate)))")
    List<StatisticsDto> getOrderStatistics();
}
