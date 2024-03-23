package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.HallType;
import lombok.Data;

@Data
public class SeatRowHallDto {
    private Long id;
    private Long locationId;
    private String name;
    private HallType type;
}
