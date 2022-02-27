package com.barisaslan.readingisgood.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document
public class Book {

    @Id
    private String id;

    @Version
    private Long version;

    @NotNull
    @Size(max = 250)
    private String name;

    @Min(value = 0)
    private long stockCount;

}
