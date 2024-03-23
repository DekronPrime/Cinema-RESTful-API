package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.MovieDto;
import com.miromax.cinema.dtos.MoviePostDto;
import com.miromax.cinema.entities.Actor;
import com.miromax.cinema.entities.Director;
import com.miromax.cinema.entities.Genre;
import com.miromax.cinema.entities.Movie;
import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.DubLanguage;
import com.miromax.cinema.entities.enums.Format;
import com.miromax.cinema.entities.enums.Status;
import com.miromax.cinema.exceptions.MovieNotFoundException;
import com.miromax.cinema.mappers.MovieMapper;
import com.miromax.cinema.repositories.*;
import com.miromax.cinema.services.MovieService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "movies")
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    public MovieServiceImpl(MovieRepository movieRepository, CountryRepository countryRepository, ActorRepository actorRepository, DirectorRepository directorRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.countryRepository = countryRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Page<MovieDto> getAllMovies(Pageable pageable) {
        return MovieMapper.MAPPER.toMovieDtoPage(movieRepository.findAll(pageable));
    }

    @Override
    @Cacheable(key = "'movie:' + #id")
    public MovieDto findMovieById(Long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie movie = optionalMovie.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        return MovieMapper.MAPPER.toMovieDto(movie);
    }

    @Override
    public MovieDto createMovie(MoviePostDto moviePostDto) {
        Movie movie = MovieMapper.MAPPER.toMovieEntity(moviePostDto);
        movie.addGenres(genreRepository.findAllById(moviePostDto.getGenreIds()));
        movie.setCountry(countryRepository.findById(moviePostDto.getCountryId()).orElse(null));
        movie.addDirectors(directorRepository.findAllById(moviePostDto.getDirectorIds()));
        movie.addActors(actorRepository.findAllById(moviePostDto.getActorIds()));
        movie.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movie.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Movie savedMovie = movieRepository.save(movie);
        return MovieMapper.MAPPER.toMovieDto(savedMovie);
    }

    @Override
    @CachePut(key = "'updatedMovie:' + #result.id")
    public MovieDto updateMovie(Long id, MoviePostDto moviePostDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie existingMovie = optionalMovie.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        existingMovie.setTitleUkr(moviePostDto.getTitleUkr());
        existingMovie.setTitleOriginal(moviePostDto.getTitleOriginal());
        existingMovie.setPosterUrl(moviePostDto.getPosterUrl());
        existingMovie.setYear(moviePostDto.getYear());
        existingMovie.removeGenres(existingMovie.getGenres());
        existingMovie.addGenres(genreRepository.findAllById(moviePostDto.getGenreIds()));
        existingMovie.setDubLanguage(moviePostDto.getDubLanguage());
        existingMovie.setCountry(countryRepository.findById(moviePostDto.getCountryId()).orElse(null));
        existingMovie.removeDirectors(existingMovie.getDirectors());
        existingMovie.addDirectors(directorRepository.findAllById(moviePostDto.getDirectorIds()));
        existingMovie.setAgeLimit(moviePostDto.getAgeLimit());
        existingMovie.setDuration(moviePostDto.getDuration());
        existingMovie.setImdbRating(moviePostDto.getImdbRating());
        existingMovie.setDescription(moviePostDto.getDescription());
        existingMovie.setTrailerUrl(moviePostDto.getTrailerUrl());
        existingMovie.setFormat(moviePostDto.getFormat());
        existingMovie.setLikes(moviePostDto.getLikes());
        existingMovie.setDislikes(moviePostDto.getDislikes());
        existingMovie.removeActors(existingMovie.getActors());
        existingMovie.addActors(actorRepository.findAllById(moviePostDto.getActorIds()));
        existingMovie.setStartRentalDate(moviePostDto.getStartRentalDate());
        existingMovie.setFinalRentalDate(moviePostDto.getFinalRentalDate());
        existingMovie.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        existingMovie.setStatus(moviePostDto.getStatus());
        Movie updatedMovie = movieRepository.save(existingMovie);
        return MovieMapper.MAPPER.toMovieDto(updatedMovie);
    }

    @Override
    @CachePut(key = "'patchedMovie:' + #result.id")
    public MovieDto patchMovie(Long id, MoviePostDto moviePostDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie existingMovie = optionalMovie.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        if (moviePostDto.getTitleUkr() != null)
            existingMovie.setTitleUkr(moviePostDto.getTitleUkr());
        if (moviePostDto.getTitleOriginal() != null)
            existingMovie.setTitleOriginal(moviePostDto.getTitleOriginal());
        if (moviePostDto.getPosterUrl() != null)
            existingMovie.setPosterUrl(moviePostDto.getPosterUrl());
        if (moviePostDto.getYear() != null)
            existingMovie.setYear(moviePostDto.getYear());
        if (moviePostDto.getGenreIds() != null) {
            existingMovie.removeGenres(existingMovie.getGenres());
            existingMovie.addGenres(genreRepository.findAllById(moviePostDto.getGenreIds()));
        }
        if (moviePostDto.getDubLanguage() != null)
            existingMovie.setDubLanguage(moviePostDto.getDubLanguage());
        if (moviePostDto.getCountryId() != null)
            existingMovie.setCountry(countryRepository.findById(moviePostDto.getCountryId()).orElse(null));
        if (moviePostDto.getDirectorIds() != null) {
            existingMovie.removeDirectors(existingMovie.getDirectors());
            existingMovie.addDirectors(directorRepository.findAllById(moviePostDto.getDirectorIds()));
        }
        if (moviePostDto.getAgeLimit() != null)
            existingMovie.setAgeLimit(moviePostDto.getAgeLimit());
        if (moviePostDto.getDuration() != null)
            existingMovie.setDuration(moviePostDto.getDuration());
        if (moviePostDto.getImdbRating() != null)
            existingMovie.setImdbRating(moviePostDto.getImdbRating());
        if (moviePostDto.getDescription() != null)
            existingMovie.setDescription(moviePostDto.getDescription());
        if (moviePostDto.getTrailerUrl() != null)
            existingMovie.setTrailerUrl(moviePostDto.getTrailerUrl());
        if (moviePostDto.getFormat() != null)
            existingMovie.setFormat(moviePostDto.getFormat());
        if (moviePostDto.getLikes() != null)
            existingMovie.setLikes(moviePostDto.getLikes());
        if (moviePostDto.getDislikes() != null)
            existingMovie.setDislikes(moviePostDto.getDislikes());
        if (moviePostDto.getActorIds() != null) {
            existingMovie.removeActors(existingMovie.getActors());
            existingMovie.addActors(actorRepository.findAllById(moviePostDto.getActorIds()));
        }
        if (moviePostDto.getStartRentalDate() != null)
            existingMovie.setStartRentalDate(moviePostDto.getStartRentalDate());
        if (moviePostDto.getFinalRentalDate() != null)
            existingMovie.setFinalRentalDate(moviePostDto.getFinalRentalDate());
        existingMovie.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        if (moviePostDto.getStatus() != null)
            existingMovie.setStatus(moviePostDto.getStatus());
        Movie patchedMovie = movieRepository.save(existingMovie);
        return MovieMapper.MAPPER.toMovieDto(patchedMovie);
    }

    @Override
    @CacheEvict(key = "'deletedMovie:' + #id")
    public MovieDto deleteMovie(Long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie existingMovie = optionalMovie.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        existingMovie.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        existingMovie.setStatus(Status.DELETED);
        Movie deletedMovie = movieRepository.save(existingMovie);
        return MovieMapper.MAPPER.toMovieDto(deletedMovie);
    }

    @Override
    public Page<MovieDto> findMoviesByYear(Integer year, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByYearIs(year, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByYearBetween(Integer leftEdge, Integer rightEdge, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByYearBetween(leftEdge, rightEdge, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByGenreId(Long genreId, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByGenresContaining(genreRepository.getReferenceById(genreId), pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByGenreIds(List<Long> genreIds, Pageable pageable) {
        List<Genre> genres = genreRepository.findAllById(genreIds);
        Page<Movie> moviePage = movieRepository.findByGenresIn(genres, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByDubLanguage(DubLanguage dubLanguage, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByDubLanguageIs(dubLanguage, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByCountryId(Long countryId, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByCountryId(countryId, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByDirectorIds(List<Long> directorIds, Pageable pageable) {
        List<Director> directors = directorRepository.findAllById(directorIds);
        Page<Movie> moviePage = movieRepository.findByDirectorsIn(directors, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByAgeLimit(AgeLimit ageLimit, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByAgeLimitIs(ageLimit, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByImdbRatingBetween(Double leftEdge, Double rightEdge, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByImdbRatingBetween(leftEdge, rightEdge, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByFormat(Format format, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByFormatIs(format, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }

    @Override
    public Page<MovieDto> findMoviesByActorIds(List<Long> actorIds, Pageable pageable) {
        List<Actor> actors = actorRepository.findAllById(actorIds);
        Page<Movie> moviePage = movieRepository.findByActorsIn(actors, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }
    @Override
    public Page<MovieDto> findMoviesByStatus(Status status, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByStatusIs(status, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }
    @Override
    public Page<MovieDto> getMoviesSortedBy(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }
    @Override
    public Page<MovieDto> searchMovies(String value, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.searchMovies(value, pageable);
        return MovieMapper.MAPPER.toMovieDtoPage(moviePage);
    }
}