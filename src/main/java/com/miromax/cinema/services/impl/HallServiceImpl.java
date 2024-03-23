package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.HallPostDto;
import com.miromax.cinema.entities.Hall;
import com.miromax.cinema.entities.enums.HallType;
import com.miromax.cinema.exceptions.HallNotFoundException;
import com.miromax.cinema.mappers.HallMapper;
import com.miromax.cinema.repositories.HallRepository;
import com.miromax.cinema.repositories.LocationRepository;
import com.miromax.cinema.services.HallService;
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
@CacheConfig(cacheNames = "halls")
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;
    private final LocationRepository locationRepository;

    public HallServiceImpl(HallRepository hallRepository, LocationRepository locationRepository) {
        this.hallRepository = hallRepository;
        this.locationRepository = locationRepository;
    }


    @Override
    public List<HallDto> getAllHalls() {
        return HallMapper.MAPPER.toHallDtoList(hallRepository.findAll());
    }

    @Override
    @Cacheable(key = "'hall:' + #id")
    public HallDto findHallById(Long id) {
        Optional<Hall> optionalHall = hallRepository.findById(id);
        Hall hall = optionalHall.orElseThrow(() -> new HallNotFoundException("Hall not found with id: " + id));
        return HallMapper.MAPPER.toHallDto(hall);
    }

    @Override
    public HallDto createHall(HallPostDto hallPostDto) {
        Hall hall = HallMapper.MAPPER.toHallEntity(hallPostDto);
        hall.setLocation(locationRepository.getReferenceById(hallPostDto.getLocationId()));
        hall.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        hall.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Hall savedHall = hallRepository.save(hall);
        return HallMapper.MAPPER.toHallDto(savedHall);
    }

    @Override
    @CachePut(key = "'updatedHall:' + #result.id")
    public HallDto updateHall(Long id, HallPostDto hallPostDto) {
        Optional<Hall> optionalHall = hallRepository.findById(id);
        Hall existingHall = optionalHall.orElseThrow(() -> new HallNotFoundException("Hall not found with id: " + id));
        existingHall.setLocation(locationRepository.getReferenceById(hallPostDto.getLocationId()));
        existingHall.setName(hallPostDto.getName());
        existingHall.setType(hallPostDto.getType());
        existingHall.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Hall updatedHall = hallRepository.save(existingHall);
        return HallMapper.MAPPER.toHallDto(updatedHall);
    }

    @Override
    @CachePut(key = "'patchedHall:' + #result.id")
    public HallDto patchHall(Long id, HallPostDto hallPostDto) {
        Optional<Hall> optionalHall = hallRepository.findById(id);
        Hall existingHall = optionalHall.orElseThrow(() -> new HallNotFoundException("Hall not found with id: " + id));
        if (hallPostDto.getLocationId() != null)
            existingHall.setLocation(locationRepository.getReferenceById(hallPostDto.getLocationId()));
        if (hallPostDto.getName() != null)
            existingHall.setName(hallPostDto.getName());
        if (hallPostDto.getType() != null)
            existingHall.setType(hallPostDto.getType());
        existingHall.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Hall patchedHall = hallRepository.save(existingHall);
        return HallMapper.MAPPER.toHallDto(patchedHall);
    }

    @Override
    @CacheEvict(key = "'deletedHall:' + #id")
    public void deleteHallById(Long id) {
        hallRepository.deleteById(id);
    }

    @Override
    public List<HallDto> findHallsByLocationId(Long locationId) {
        return HallMapper.MAPPER.toHallDtoList(hallRepository.findByLocationId(locationId));
    }

    @Override
    public List<HallDto> findHallsByName(String name) {
        return HallMapper.MAPPER.toHallDtoList(hallRepository.findByNameIs(name));
    }

    @Override
    public List<HallDto> findHallsByType(HallType type) {
        return HallMapper.MAPPER.toHallDtoList(hallRepository.findByTypeIs(type));
    }

    @Override
    public List<HallDto> findHallsSortedBy(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        return HallMapper.MAPPER.toHallDtoList(hallRepository.findAll(sort));
    }

}
