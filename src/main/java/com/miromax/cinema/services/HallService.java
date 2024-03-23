package com.miromax.cinema.services;

import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.HallPostDto;
import com.miromax.cinema.entities.enums.HallType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HallService {
    List<HallDto> getAllHalls();
    HallDto findHallById(Long id);
    HallDto createHall(HallPostDto hallPostDto);
    HallDto updateHall(Long id, HallPostDto hallPostDto);
    HallDto patchHall(Long id, HallPostDto hallPostDto);
    void deleteHallById(Long id);
    List<HallDto> findHallsByLocationId(Long locationId);
    List<HallDto> findHallsByName(String name);
    List<HallDto> findHallsByType(HallType type);
    List<HallDto> findHallsSortedBy(String sortBy, String sortDirection);
}
