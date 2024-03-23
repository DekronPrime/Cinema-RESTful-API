package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Actor;
import com.miromax.cinema.entities.Director;
import com.miromax.cinema.entities.Genre;
import com.miromax.cinema.entities.Movie;
import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.DubLanguage;
import com.miromax.cinema.entities.enums.Format;
import com.miromax.cinema.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByYearIs(Integer year, Pageable pageable);
    Page<Movie> findByYearBetween(Integer leftEdge, Integer rightEdge, Pageable pageable);
    Page<Movie> findByGenresContaining(Genre genre, Pageable pageable);
    Page<Movie> findByGenresIn(List<Genre> genres, Pageable pageable);
    Page<Movie> findByDubLanguageIs(DubLanguage dubLanguage, Pageable pageable);
    Page<Movie> findByCountryId(Long countryId, Pageable pageable);
    Page<Movie> findByDirectorsIn(List<Director> directors, Pageable pageable);
    Page<Movie> findByAgeLimitIs(AgeLimit ageLimit, Pageable pageable);
    Page<Movie> findByImdbRatingBetween(Double leftEdge, Double rightEdge, Pageable pageable);
    Page<Movie> findByFormatIs(Format format, Pageable pageable);
    Page<Movie> findByActorsIn(List<Actor> actors, Pageable pageable);
    Page<Movie> findByStatusIs(Status status, Pageable pageable);
    @Query("SELECT m FROM Movie m WHERE " +
            "LOWER(m.titleOriginal) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(m.titleUkr) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(m.description) LIKE LOWER(CONCAT('%', :value, '%'))")
    Page<Movie> searchMovies(String value, Pageable pageable);
}
