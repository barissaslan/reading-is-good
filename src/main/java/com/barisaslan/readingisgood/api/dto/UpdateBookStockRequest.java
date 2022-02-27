package com.barisaslan.readingisgood.api.dto;


import com.barisaslan.readingisgood.domain.dto.UpdateBookStockDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookStockRequest {

    @NotNull
    private String id;

    private long stockChangeCount;

    public UpdateBookStockDto toModel() {
        return UpdateBookStockDto.builder()
                .bookId(id)
                .stockChangeCount(stockChangeCount)
                .build();
    }

}
