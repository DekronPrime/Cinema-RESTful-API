package com.miromax.cinema.services;

import com.miromax.cinema.dtos.CommentDto;
import com.miromax.cinema.dtos.CommentPostDto;
import com.miromax.cinema.entities.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<CommentDto> getAllComments(Pageable pageable);
    CommentDto findCommentById(Long id);
    Page<CommentDto> findCommentsByMovieId(Long movieId, Pageable pageable);
    Page<CommentDto> findCommentsByUserId(Long userId, Pageable pageable);
    CommentDto createComment(CommentPostDto commentPostDto);
    CommentDto updateComment(Long id, CommentPostDto commentPostDto);
    CommentDto patchComment(Long id, CommentPostDto commentPostDto);
    CommentDto deleteCommentByID(Long id);
    Page<CommentDto> getCommentsByStatus(CommentStatus status, Pageable pageable);
    Page<CommentDto> getCommentsSortedBy(Pageable pageable);
    Page<CommentDto> searchComments(String value, Pageable pageable);
}
