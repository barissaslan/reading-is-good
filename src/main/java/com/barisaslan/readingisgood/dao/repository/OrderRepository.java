package com.barisaslan.readingisgood.dao.repository;

import com.barisaslan.readingisgood.dao.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByCreatedDateBetween(Date startDate, Date endDate);

}
