package com.barisaslan.readingisgood.api.dto;


import com.barisaslan.readingisgood.domain.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {

    @NotNull
    @Size(max = 250)
    private String name;

    @Positive
    private long stockCount;

    public BookDto toModel() {
        return BookDto.builder()
                .name(name)
                .stockCount(stockCount)
                .build();
    }

}
