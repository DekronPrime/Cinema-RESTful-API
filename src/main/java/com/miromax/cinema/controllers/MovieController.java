package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.*;
import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.DubLanguage;
import com.miromax.cinema.entities.enums.Format;
import com.miromax.cinema.entities.enums.Status;
import com.miromax.cinema.services.CommentService;
import com.miromax.cinema.services.MovieService;
import com.miromax.cinema.services.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {
    private final MovieService movieService;
    private final CommentService commentService;
    private final SessionService sessionService;

    public MovieController(MovieService movieService, CommentService commentService, SessionService sessionService) {
        this.movieService = movieService;
        this.commentService = commentService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> moviePage = movieService.getAllMovies(PageRequest.of(page, size, Sort.by("id")));
        return ResponseEntity.ok(moviePage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<MovieDto>> getMovieByYear(
            @RequestParam Integer year,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByYear(year, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-years")
    public ResponseEntity<List<MovieDto>> getMovieByYears(
            @RequestParam Integer leftEdge,
            @RequestParam Integer rightEdge,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByYearBetween(leftEdge, rightEdge, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-genre")
    public ResponseEntity<List<MovieDto>> getMovieByGenre(
            @RequestParam Long id,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByGenreId(id, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-genres")
    public ResponseEntity<List<MovieDto>> getMovieByGenres(
            @RequestParam List<Long> ids,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByGenreIds(ids, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-dub")
    public ResponseEntity<List<MovieDto>> getMovieByDubLanguage(
            @RequestParam DubLanguage dub,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByDubLanguage(dub, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<MovieDto>> getMovieByCountry(
            @RequestParam Long id,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByCountryId(id, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-directors")
    public ResponseEntity<List<MovieDto>> getMovieByDirectors(
            @RequestParam List<Long> ids,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByDirectorIds(ids, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-age")
    public ResponseEntity<List<MovieDto>> getMovieByAgeLimit(
            @RequestParam AgeLimit age,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByAgeLimit(age, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-imdb")
    public ResponseEntity<List<MovieDto>> getMovieByImdb(
            @RequestParam Double leftEdge,
            @RequestParam Double rightEdge,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByImdbRatingBetween(leftEdge, rightEdge, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-format")
    public ResponseEntity<List<MovieDto>> getMovieByFormat(
            @RequestParam Format format,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByFormat(format, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-actors")
    public ResponseEntity<List<MovieDto>> getMovieByActors(
            @RequestParam List<Long> ids,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByActorIds(ids, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<MovieDto>> getMovieByStatus(
            @RequestParam Status status,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.findMoviesByStatus(status, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/sort")
    public ResponseEntity<List<MovieDto>> getMoviesSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<MovieDto> movieDtoPage = movieService.getMoviesSortedBy(PageRequest.of(page, size, Sort.by(direction, sortBy)));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(
            @RequestParam String value,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<MovieDto> movieDtoPage = movieService.searchMovies(value, PageRequest.of(page, size));
        return ResponseEntity.ok(movieDtoPage.getContent());
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MoviePostDto moviePostDto) {
        MovieDto createdMovie = movieService.createMovie(moviePostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMovie.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Long id,
            @RequestBody MoviePostDto moviePostDto) {
        MovieDto movieDto = movieService.updateMovie(id, moviePostDto);
        return ResponseEntity.ok(movieDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> patchMovie(
            @PathVariable Long id,
            @RequestBody MoviePostDto moviePostDto) {
        MovieDto movieDto = movieService.patchMovie(id, moviePostDto);
        return ResponseEntity.ok(movieDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsFromMovie(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CommentDto> commentDtoPage = commentService.findCommentsByMovieId(id, PageRequest.of(page, size, Sort.by("writtenAt")));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<SessionDto>> getAllSessionsFromMovie(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<SessionDto> sessionDtoPage = sessionService.findSessionsByMovieId(id, PageRequest.of(page, size, Sort.by("createdAt")));
        return ResponseEntity.ok(sessionDtoPage.getContent());
    }
}