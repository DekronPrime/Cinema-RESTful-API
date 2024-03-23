package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.CityDto;
import com.miromax.cinema.dtos.LocationDto;
import com.miromax.cinema.dtos.LocationPostDto;
import com.miromax.cinema.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CityDto.class})
public interface LocationMapper {
    LocationMapper MAPPER = Mappers.getMapper(LocationMapper.class);

    Location toLocationEntity(LocationPostDto locationPostDto);
    @Mapping(target = "city.countryId", source = "city.country.id")
    LocationDto toLocationDto(Location location);

    default List<LocationDto> toLocationDtoList(List<Location> locationList) {
        return locationList.stream()
                .map(this::toLocationDto)
                .collect(Collectors.toList());
    }
}