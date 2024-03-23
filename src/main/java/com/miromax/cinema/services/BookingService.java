package com.miromax.cinema.services;

import com.miromax.cinema.dtos.BookingDto;
import com.miromax.cinema.dtos.BookingPostDto;
import com.miromax.cinema.dtos.BookingSessionSeatsDto;
import com.miromax.cinema.entities.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    Page<BookingDto> getAllBookings(Pageable pageable);
    BookingDto findBookingById(Long id);
    List<BookingSessionSeatsDto> findBookingsBySessionId(Long sessionId);
    BookingDto createBooking(BookingPostDto bookingPostDto);
    BookingDto updateBooking(Long id, BookingPostDto bookingPostDto);
    BookingDto patchBooking(Long id, BookingPostDto bookingPostDto);
    void deleteBooking(Long id);
    Page<BookingDto> findBookingsBySessionId(Long sessionId, Pageable pageable);
    Page<BookingDto> findBookingsByUserId(Long userId, Pageable pageable);
    Page<BookingDto> findBookingsBySeatRowId(Long seatRowId, Pageable pageable);
    Page<BookingDto> getBookingsByStatus(BookingStatus status, Pageable pageable);
    Page<BookingDto> getBookingsSortedBy(Pageable pageable);
    Page<BookingDto> searchBookings(String value, Pageable pageable);
}
