package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.SeatRowDto;
import com.miromax.cinema.dtos.SeatRowPostDto;
import com.miromax.cinema.entities.SeatRow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SeatRowMapper {
    SeatRowMapper MAPPER = Mappers.getMapper(SeatRowMapper.class);

    SeatRow toSeatRowEntity(SeatRowPostDto seatRowPostDto);
    @Mapping(target = "hall.locationId", source = "hall.location.id")
    SeatRowDto toSeatRowDto(SeatRow seatRow);

    default List<SeatRowDto> toSeatRowDtoList(List<SeatRow> seatRowList) {
        return seatRowList.stream()
                .map(this::toSeatRowDto)
                .collect(Collectors.toList());
    }
}