package com.miromax.cinema.services;

import com.miromax.cinema.dtos.MovieDto;
import com.miromax.cinema.dtos.MoviePostDto;
import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.DubLanguage;
import com.miromax.cinema.entities.enums.Format;
import com.miromax.cinema.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    Page<MovieDto> getAllMovies(Pageable pageable);
    MovieDto findMovieById(Long id);
    MovieDto createMovie(MoviePostDto moviePostDto);
    MovieDto updateMovie(Long id, MoviePostDto moviePostDto);
    MovieDto patchMovie(Long id, MoviePostDto moviePostDto);
    MovieDto deleteMovie(Long id);
    Page<MovieDto> findMoviesByYear(Integer year, Pageable pageable);
    Page<MovieDto> findMoviesByYearBetween(Integer leftEdge, Integer rightEdge, Pageable pageable);
    Page<MovieDto> findMoviesByGenreId(Long genreId, Pageable pageable);
    Page<MovieDto> findMoviesByGenreIds(List<Long> genreIds, Pageable pageable);
    Page<MovieDto> findMoviesByDubLanguage(DubLanguage dubLanguage, Pageable pageable);
    Page<MovieDto> findMoviesByCountryId(Long countryId, Pageable pageable);
    Page<MovieDto> findMoviesByDirectorIds(List<Long> directorIds, Pageable pageable);
    Page<MovieDto> findMoviesByAgeLimit(AgeLimit ageLimit, Pageable pageable);
    Page<MovieDto> findMoviesByImdbRatingBetween(Double leftEdge, Double rightEdge, Pageable pageable);
    Page<MovieDto> findMoviesByFormat(Format format, Pageable pageable);
    Page<MovieDto> findMoviesByActorIds(List<Long> actorIds, Pageable pageable);
    Page<MovieDto> findMoviesByStatus(Status status, Pageable pageable);
    Page<MovieDto> getMoviesSortedBy(Pageable pageable);
    Page<MovieDto> searchMovies(String value, Pageable pageable);
}
