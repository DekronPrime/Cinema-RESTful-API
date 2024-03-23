package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.HallType;
import lombok.Data;

@Data
public class SessionHallDto {
    private Long id;
    private HallLocationDto location;
    private String name;
    private HallType type;

}
