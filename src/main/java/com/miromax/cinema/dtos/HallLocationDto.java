package com.miromax.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class HallLocationDto {
    private Long id;
    private Long cityId;
    private String cityName;
    private String shoppingMall;
    private String address;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String details;
}