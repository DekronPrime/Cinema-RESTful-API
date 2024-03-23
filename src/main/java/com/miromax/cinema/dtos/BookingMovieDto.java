package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.Format;
import lombok.Data;

@Data
public class BookingMovieDto {
    private Long id;
    private String titleUkr;
    private Integer year;
    private Double imdbRating;
    private Format format;
    private AgeLimit ageLimit;
}
