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
public class MoviePostDto {
    private String titleUkr;
    private String titleOriginal;
    private String posterUrl;
    private Integer year;
    private List<Long> genreIds;
    private DubLanguage dubLanguage;
    private Long countryId;
    private List<Long> directorIds;
    private AgeLimit ageLimit;
    private Integer duration;
    private Double imdbRating;
    private String description;
    private String trailerUrl;
    private Format format;
    private Integer likes;
    private Integer dislikes;
    private List<Long> actorIds;
    private LocalDate startRentalDate;
    private LocalDate finalRentalDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}