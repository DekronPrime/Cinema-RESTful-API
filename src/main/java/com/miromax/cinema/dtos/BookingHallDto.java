package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.HallType;
import lombok.Data;

@Data
public class BookingHallDto {
    private Long id;
    private BookingHallLocationDto location;
    private String name;
    private HallType type;
}
