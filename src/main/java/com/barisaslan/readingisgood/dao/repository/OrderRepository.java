package com.barisaslan.readingisgood.dao.repository;

import com.barisaslan.readingisgood.dao.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

}
