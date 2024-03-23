package com.miromax.cinema.dtos;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LocationPostDto {
    private Long id;
    private Long cityId;
    private String shoppingMall;
    private String address;
    private String details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
