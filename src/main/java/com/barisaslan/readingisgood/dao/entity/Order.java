package com.barisaslan.readingisgood.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@Data
@Document
public class Order {

    @Id
    private String id;

    private BigDecimal totalPrice;

    private OrderStatus status;

    private Date createdDate;

    private HashMap<String, Long> orderItems;

    @JsonIgnore
    @DocumentReference
    private Customer customer;

}
