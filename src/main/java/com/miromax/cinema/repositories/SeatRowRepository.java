package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.SeatRow;
import com.miromax.cinema.entities.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRowRepository extends JpaRepository<SeatRow, Long> {
    List<SeatRow> findByHallId(Long hallId);
    List<SeatRow> findByNumberIs(Integer number);
    List<SeatRow> findByTypeIs(SeatType type);
    List<SeatRow> findByPriceIs(Integer price);
}
