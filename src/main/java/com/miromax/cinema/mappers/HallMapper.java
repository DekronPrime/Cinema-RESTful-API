package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.HallPostDto;
import com.miromax.cinema.entities.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HallMapper {
    HallMapper MAPPER = Mappers.getMapper(HallMapper.class);

    Hall toHallEntity(HallPostDto hallPostDto);
    @Mapping(target = "location.cityId", source = "location.city.id")
    @Mapping(target = "location.cityName", source = "location.city.name")
    HallDto toHallDto(Hall hall);

    default List<HallDto> toHallDtoList(List<Hall> hallList) {
        return hallList.stream()
                .map(this::toHallDto)
                .collect(Collectors.toList());
    }
}