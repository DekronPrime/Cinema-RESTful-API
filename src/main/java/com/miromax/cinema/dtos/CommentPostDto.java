package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.CommentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentPostDto {
    private Long id;
    private Long userId;
    private Long movieId;
    private String content;
    private LocalDateTime writtenAt;
    private LocalDateTime updatedAt;
    private Integer likes;
    private Integer dislikes;
    private CommentStatus status;
}
