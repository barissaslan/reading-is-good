package com.barisaslan.readingisgood.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private String id;
    private String name;
    private long stockCount;

}
