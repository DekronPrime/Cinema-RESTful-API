package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.LocationDto;
import com.miromax.cinema.dtos.LocationPostDto;
import com.miromax.cinema.entities.Location;
import com.miromax.cinema.exceptions.LocationNotFoundException;
import com.miromax.cinema.mappers.LocationMapper;
import com.miromax.cinema.repositories.CityRepository;
import com.miromax.cinema.repositories.LocationRepository;
import com.miromax.cinema.services.LocationService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "locations")
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final CityRepository cityRepository;

    public LocationServiceImpl(LocationRepository locationRepository, CityRepository cityRepository) {
        this.locationRepository = locationRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<LocationDto> getAllLocations() {
        return LocationMapper.MAPPER.toLocationDtoList(locationRepository.findAll());
    }

    @Override
    @Cacheable(key = "'location:' + #id")
    public LocationDto findLocationById(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        Location location = optionalLocation.orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));
        return LocationMapper.MAPPER.toLocationDto(location);
    }

    @Override
    public LocationDto createLocation(LocationPostDto locationPostDto) {
        Location location = LocationMapper.MAPPER.toLocationEntity(locationPostDto);
        location.setCity(cityRepository.findById(locationPostDto.getCityId()).orElse(null));
        location.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        location.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Location savedLocation = locationRepository.save(location);
        return LocationMapper.MAPPER.toLocationDto(savedLocation);
    }

    @Override
    @CachePut(key = "'updatedLocation:' + #result.id")
    public LocationDto updateLocation(Long id, LocationPostDto locationPostDto) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        Location existingLocation = optionalLocation.orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));
        existingLocation.setCity(cityRepository.findById(locationPostDto.getCityId()).orElse(null));
        existingLocation.setShoppingMall(locationPostDto.getShoppingMall());
        existingLocation.setAddress(locationPostDto.getAddress());
        existingLocation.setDetails(locationPostDto.getDetails());
        existingLocation.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Location updatedLocation = locationRepository.save(existingLocation);
        return LocationMapper.MAPPER.toLocationDto(updatedLocation);
    }

    @Override
    @CachePut(key = "'patchedLocation:' + #result.id")
    public LocationDto patchLocation(Long id, LocationPostDto locationPostDto) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        Location existingLocation = optionalLocation.orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));
        if (locationPostDto.getCityId() != null)
            existingLocation.setCity(cityRepository.findById(locationPostDto.getCityId()).orElse(null));
        if (locationPostDto.getShoppingMall() != null)
            existingLocation.setShoppingMall(locationPostDto.getShoppingMall());
        if (locationPostDto.getAddress() != null)
            existingLocation.setAddress(locationPostDto.getAddress());
        if (locationPostDto.getDetails() != null)
            existingLocation.setDetails(locationPostDto.getDetails());
        existingLocation.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Location patchedLocation = locationRepository.save(existingLocation);
        return LocationMapper.MAPPER.toLocationDto(patchedLocation);
    }


    @Override
    @CacheEvict(key = "'deletedLocation:' + #id")
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationDto> findLocationsByCityId(Long cityId) {
        return LocationMapper.MAPPER.toLocationDtoList(locationRepository.findByCityId(cityId));
    }

    @Override
    public List<LocationDto> findLocationsSortedBy(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        return LocationMapper.MAPPER.toLocationDtoList(locationRepository.findAll(sort));
    }

    @Override
    public List<LocationDto> searchLocations(String value) {
        return LocationMapper.MAPPER.toLocationDtoList(locationRepository.searchLocations(value));
    }

}
