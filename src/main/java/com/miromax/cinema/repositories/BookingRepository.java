package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Booking;
import com.miromax.cinema.entities.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBySessionId(Long sessionId);
    Page<Booking> findBySessionId(Long sessionId, Pageable pageable);
    Page<Booking> findByUserId(Long userId, Pageable pageable);
    Page<Booking> findBySeatRowId(Long seatRowId, Pageable pageable);
    Page<Booking> findByStatusIs(BookingStatus status, Pageable pageable);
    @Query("SELECT b FROM Booking b WHERE " +
            "b.seatNumber = :value OR " +
            "b.price = :value")
    Page<Booking> searchBookings(@Param("value") String value, Pageable pageable);
}
