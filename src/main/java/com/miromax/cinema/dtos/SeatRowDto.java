package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.SeatType;
import lombok.Data;

@Data
public class SeatRowDto {
    private Long id;
    private SeatRowHallDto hall;
    private Integer number;
    private SeatType type;
    private Integer capacity;
    private Integer price;
}
