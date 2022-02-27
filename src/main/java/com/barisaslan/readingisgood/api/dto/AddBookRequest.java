package com.barisaslan.readingisgood.api.dto;


import com.barisaslan.readingisgood.domain.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {

    @NotNull
    @Size(max = 250)
    private String title;

    @NotNull
    @Positive
    private long stockCount;

    @NotNull
    @Positive
    private BigDecimal price;

    public BookDto toModel() {
        return BookDto.builder()
                .title(title)
                .stockCount(stockCount)
                .price(price)
                .build();
    }

}
