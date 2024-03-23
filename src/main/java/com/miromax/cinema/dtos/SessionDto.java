package com.miromax.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miromax.cinema.entities.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SessionDto {
    private Long id;
    private SessionMovieDto movie;
    private SessionHallDto hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int[] prices;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}
