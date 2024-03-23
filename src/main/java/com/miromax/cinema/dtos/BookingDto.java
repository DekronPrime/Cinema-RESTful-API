package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private BookingSessionDto session;
    private BookingUserDto user;
    private BookingSeatRowDto seatRow;
    private Integer seatNumber;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus status;
}
