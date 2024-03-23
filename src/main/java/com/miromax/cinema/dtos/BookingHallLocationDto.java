package com.miromax.cinema.dtos;

import lombok.Data;

@Data
public class BookingHallLocationDto {
    private Long id;
    private Long cityId;
    private String cityName;
    private String shoppingMall;
}
