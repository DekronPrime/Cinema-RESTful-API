package com.miromax.cinema.services;

import com.miromax.cinema.dtos.SeatRowDto;
import com.miromax.cinema.dtos.SeatRowPostDto;
import com.miromax.cinema.entities.enums.SeatType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatRowService {
    List<SeatRowDto> getAllSeatRows();
    SeatRowDto findSeatRowById(Long id);
    List<SeatRowDto> findSeatRowsByHallId(Long hallId);
    SeatRowDto createSeatRow(SeatRowPostDto seatRowPostDto);
    SeatRowDto updateSeatRow(Long id, SeatRowPostDto seatRowPostDto);
    SeatRowDto patchSeatRow(Long id, SeatRowPostDto seatRowPostDto);
    void deleteSeatRowById(Long id);
    List<SeatRowDto> findSeatRowsByRowNumber(Integer number);
    List<SeatRowDto> findSeatRowsByType(SeatType type);
    List<SeatRowDto> findSeatRowsByPrice(Integer price);
    List<SeatRowDto> getSeatRowsSortedBy(String sortBy, String sortDirection);
}
