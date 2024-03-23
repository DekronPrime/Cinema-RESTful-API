package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.SeatType;
import lombok.Data;

@Data
public class SeatRowPostDto {
    private Long id;
    private Long hallId;
    private Integer number;
    private SeatType type;
    private Integer capacity;
    private Integer price;
}
