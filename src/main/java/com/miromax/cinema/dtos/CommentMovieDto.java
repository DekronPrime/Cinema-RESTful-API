package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.Status;
import lombok.Data;

@Data
public class CommentMovieDto {
    private Long id;
    private String titleOriginal;
    private Integer year;
    private Double imdbRating;
    private Status status;
}
