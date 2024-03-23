package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.AgeLimit;
import com.miromax.cinema.entities.enums.Format;
import lombok.Data;

@Data
public class SessionMovieDto {
    private Long id;
    private String titleOriginal;
    private String titleUkr;
    private Integer year;
    private Double imdbRating;
    private Integer duration;
    private Format format;
    private AgeLimit ageLimit;
}
