package com.barisaslan.readingisgood.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {

    private String email;
    private String password;

}
