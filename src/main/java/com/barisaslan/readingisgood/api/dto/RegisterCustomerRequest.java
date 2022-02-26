package com.barisaslan.readingisgood.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCustomerRequest {

    @Email
    @NotNull
    @Size(max = 320)
    private String email;

    @NotNull
    private String password;

}
