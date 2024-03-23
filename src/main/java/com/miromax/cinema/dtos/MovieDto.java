package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.DubLanguage;
import com.miromax.cinema.entities.enums.Format;
import com.miromax.cinema.entities.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDto {
    private Long id;
    private String titleUkr;
    private String titleOriginal;
    private String posterUrl;
    private Integer year;
    private List<GenreDto> genres;
    private DubLanguage dubLanguage;
    private CountryDto country;
    private List<DirectorDto> directors;
    private AgeLimit ageLimit;
    private Integer duration;
    private Double imdbRating;
    private String description;
    private String trailerUrl;
    private Format format;
    private Integer likes;
    private Integer dislikes;
    private List<ActorDto> actors;
    private LocalDate startRentalDate;
    private LocalDate finalRentalDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}