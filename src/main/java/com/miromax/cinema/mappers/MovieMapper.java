package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.*;
import com.miromax.cinema.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {CountryDto.class, ActorDto.class, DirectorDto.class, GenreDto.class})
public interface MovieMapper {
    MovieMapper MAPPER = Mappers.getMapper(MovieMapper.class);

    Movie toMovieEntity(MoviePostDto moviePostDto);
    MovieDto toMovieDto(Movie movie);

    default Page<MovieDto> toMovieDtoPage(Page<Movie> moviePage) {
        return moviePage.map(this::toMovieDto);
    }
}