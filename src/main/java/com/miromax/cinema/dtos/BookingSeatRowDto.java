package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.SeatType;
import lombok.Data;

@Data
public class BookingSeatRowDto {
    private Long id;
    private Integer number;
    private SeatType type;
    private Integer capacity;
}