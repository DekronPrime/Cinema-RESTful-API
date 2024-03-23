package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingSessionDto {
    private Long id;
    private BookingMovieDto movie;
    private BookingHallDto hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;
}
