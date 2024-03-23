package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.HallType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HallDto {
    private Long id;
    private HallLocationDto location;
    private String name;
    private HallType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
