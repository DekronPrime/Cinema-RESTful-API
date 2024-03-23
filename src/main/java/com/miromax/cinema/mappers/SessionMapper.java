package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.SessionDto;
import com.miromax.cinema.dtos.SessionPostDto;
import com.miromax.cinema.entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionMapper MAPPER = Mappers.getMapper(SessionMapper.class);
    Session toSessionEntity(SessionPostDto sessionPostDto);
    @Mapping(target = "hall.location.cityId", source = "hall.location.city.id")
    @Mapping(target = "hall.location.cityName", source = "hall.location.city.name")
    SessionDto toSessionDto(Session session);

    default Page<SessionDto> toSessionDtoPage(Page<Session> sessionPage) {
        return sessionPage.map(this::toSessionDto);
    }
}