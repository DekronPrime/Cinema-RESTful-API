package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingPostDto {
    private Long sessionId;
    private Long userId;
    private Long seatRowId;
    private Integer seatNumber;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus status;
}
