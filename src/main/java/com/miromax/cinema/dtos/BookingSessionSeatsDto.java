package com.miromax.cinema.dtos;

import lombok.Data;

@Data
public class BookingSessionSeatsDto {
    private Long seatRowId;
    private Integer rowNumber;
    private Integer seatNumber;
}
