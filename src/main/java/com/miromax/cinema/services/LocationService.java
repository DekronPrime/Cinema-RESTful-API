package com.miromax.cinema.services;

import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.LocationDto;
import com.miromax.cinema.dtos.LocationPostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    List<LocationDto> getAllLocations();
    LocationDto findLocationById(Long id);
    LocationDto createLocation(LocationPostDto locationPostDto);
    LocationDto updateLocation(Long id, LocationPostDto locationPostDto);
    LocationDto patchLocation(Long id, LocationPostDto locationPostDto);
    void deleteLocationById(Long id);
    List<LocationDto> findLocationsByCityId(Long cityId);
    List<LocationDto> findLocationsSortedBy(String sortBy, String sortDirection);
    List<LocationDto> searchLocations(String value);
}
