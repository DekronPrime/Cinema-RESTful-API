package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SessionPostDto {
    private Long movieId;
    private Long hallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int[] prices;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}
