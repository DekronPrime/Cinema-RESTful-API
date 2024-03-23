package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.SeatRowDto;
import com.miromax.cinema.dtos.SeatRowPostDto;
import com.miromax.cinema.entities.SeatRow;
import com.miromax.cinema.entities.enums.SeatType;
import com.miromax.cinema.exceptions.SeatRowNotFoundException;
import com.miromax.cinema.mappers.SeatRowMapper;
import com.miromax.cinema.repositories.HallRepository;
import com.miromax.cinema.repositories.SeatRowRepository;
import com.miromax.cinema.services.SeatRowService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "seatRows")
public class SeatRowServiceImpl implements SeatRowService {
    private final SeatRowRepository seatRowRepository;
    private final HallRepository hallRepository;

    public SeatRowServiceImpl(SeatRowRepository seatRowRepository, HallRepository hallRepository) {
        this.seatRowRepository = seatRowRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public List<SeatRowDto> getAllSeatRows() {
        return SeatRowMapper.MAPPER.toSeatRowDtoList(seatRowRepository.findAll());
    }

    @Override
    @Cacheable(key = "'seatRow:' + #id")
    public SeatRowDto findSeatRowById(Long id) {
        Optional<SeatRow> optionalSeatRow = seatRowRepository.findById(id);
        SeatRow seatRow = optionalSeatRow.orElseThrow(() -> new SeatRowNotFoundException("Seat row not found with id: " + id));
        return SeatRowMapper.MAPPER.toSeatRowDto(seatRow);
    }

    @Override
    public List<SeatRowDto> findSeatRowsByHallId(Long hallId) {
        List<SeatRow> seatRowList = seatRowRepository.findByHallId(hallId);
        return SeatRowMapper.MAPPER.toSeatRowDtoList(seatRowList);
    }

    @Override
    public SeatRowDto createSeatRow(SeatRowPostDto seatRowPostDto) {
        SeatRow seatRow = SeatRowMapper.MAPPER.toSeatRowEntity(seatRowPostDto);
        seatRow.setHall(hallRepository.getReferenceById(seatRowPostDto.getHallId()));
        seatRow.setPrice(0);
        SeatRow createdSeatRow = seatRowRepository.save(seatRow);
        return SeatRowMapper.MAPPER.toSeatRowDto(createdSeatRow);
    }

    @Override
    @CachePut(key = "'updatedSeatRow:' + #result.id")
    public SeatRowDto updateSeatRow(Long id, SeatRowPostDto seatRowPostDto) {
        Optional<SeatRow> optionalSeatRow = seatRowRepository.findById(id);
        SeatRow existingSeatRow = optionalSeatRow.orElseThrow(() -> new SeatRowNotFoundException("Seat row not found with id: " + id));
        existingSeatRow.setHall(hallRepository.getReferenceById(seatRowPostDto.getHallId()));
        existingSeatRow.setNumber(seatRowPostDto.getNumber());
        existingSeatRow.setType(seatRowPostDto.getType());
        existingSeatRow.setCapacity(seatRowPostDto.getCapacity());
        existingSeatRow.setPrice(seatRowPostDto.getPrice());
        SeatRow updatedSeatRow = seatRowRepository.save(existingSeatRow);
        return SeatRowMapper.MAPPER.toSeatRowDto(updatedSeatRow);
    }

    @Override
    @CachePut(key = "'patchedSeatRow:' + #result.id")
    public SeatRowDto patchSeatRow(Long id, SeatRowPostDto seatRowPostDto) {
        Optional<SeatRow> optionalSeatRow = seatRowRepository.findById(id);
        SeatRow existingSeatRow = optionalSeatRow.orElseThrow(() -> new SeatRowNotFoundException("Seat row not found with id: " + id));
        if (seatRowPostDto.getHallId() != null)
            existingSeatRow.setHall(hallRepository.getReferenceById(seatRowPostDto.getHallId()));
        if (seatRowPostDto.getNumber() != null)
            existingSeatRow.setNumber(seatRowPostDto.getNumber());
        if (seatRowPostDto.getType() != null)
            existingSeatRow.setType(seatRowPostDto.getType());
        if (seatRowPostDto.getCapacity() != null)
            existingSeatRow.setCapacity(seatRowPostDto.getCapacity());
        if (seatRowPostDto.getPrice() != null)
            existingSeatRow.setPrice(seatRowPostDto.getPrice());
        SeatRow patchedSeatRow = seatRowRepository.save(existingSeatRow);
        return SeatRowMapper.MAPPER.toSeatRowDto(patchedSeatRow);
    }

    @Override
    @CacheEvict(key = "'deletedSeatRow:' + #id")
    public void deleteSeatRowById(Long id) {
        seatRowRepository.deleteById(id);
    }

    @Override
    public List<SeatRowDto> findSeatRowsByRowNumber(Integer number) {
        return SeatRowMapper.MAPPER.toSeatRowDtoList(seatRowRepository.findByNumberIs(number));
    }

    @Override
    public List<SeatRowDto> findSeatRowsByType(SeatType type) {
        return SeatRowMapper.MAPPER.toSeatRowDtoList(seatRowRepository.findByTypeIs(type));
    }

    @Override
    public List<SeatRowDto> findSeatRowsByPrice(Integer price) {
        return SeatRowMapper.MAPPER.toSeatRowDtoList(seatRowRepository.findByPriceIs(price));
    }

    @Override
    public List<SeatRowDto> getSeatRowsSortedBy(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        return SeatRowMapper.MAPPER.toSeatRowDtoList(seatRowRepository.findAll(sort));
    }
}
