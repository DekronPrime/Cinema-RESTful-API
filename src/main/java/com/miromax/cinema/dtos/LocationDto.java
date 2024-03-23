package com.miromax.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationDto {
    private Long id;
    private CityDto city;
    private String shoppingMall;
    private String address;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
