package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.CommentDto;
import com.miromax.cinema.dtos.CommentPostDto;
import com.miromax.cinema.entities.Comment;
import com.miromax.cinema.entities.enums.CommentStatus;
import com.miromax.cinema.exceptions.CommentNotFoundException;
import com.miromax.cinema.mappers.CommentMapper;
import com.miromax.cinema.repositories.CommentRepository;
import com.miromax.cinema.repositories.MovieRepository;
import com.miromax.cinema.repositories.UserRepository;
import com.miromax.cinema.services.CommentService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "comments")
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Page<CommentDto> getAllComments(Pageable pageable) {
        return CommentMapper.MAPPER.toCommentDtoPage(commentRepository.findAll(pageable));
    }

    @Override
    @Cacheable(key = "'comment:' + #id")
    public CommentDto findCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment comment = optionalComment.orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        return CommentMapper.MAPPER.toCommentDto(comment);
    }

    @Override
    public Page<CommentDto> findCommentsByMovieId(Long movieId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findByMovieId(movieId, pageable);
        return CommentMapper.MAPPER.toCommentDtoPage(commentPage);
    }

    @Override
    public Page<CommentDto> findCommentsByUserId(Long userId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findByUserId(userId, pageable);
        return CommentMapper.MAPPER.toCommentDtoPage(commentPage);
    }

    @Override
    public CommentDto createComment(CommentPostDto commentPostDto) {
        Comment comment = CommentMapper.MAPPER.toCommentEntity(commentPostDto);
        comment.setUser(userRepository.getReferenceById(commentPostDto.getUserId()));
        comment.setMovie(movieRepository.getReferenceById(commentPostDto.getMovieId()));
        comment.setLikes(0);
        comment.setDislikes(0);
        comment.setWrittenAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        comment.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.MAPPER.toCommentDto(savedComment);
    }

    @Override
    @CachePut(key = "'updatedComment:' + #result.id")
    public CommentDto updateComment(Long id, CommentPostDto commentPostDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment existingComment = optionalComment.orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        existingComment.setUser(userRepository.getReferenceById(commentPostDto.getUserId()));
        existingComment.setMovie(movieRepository.getReferenceById(commentPostDto.getMovieId()));
        existingComment.setContent(commentPostDto.getContent());
        existingComment.setLikes(commentPostDto.getLikes());
        existingComment.setDislikes(commentPostDto.getDislikes());
        existingComment.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        existingComment.setStatus(commentPostDto.getStatus());
        Comment updatedComment = commentRepository.save(existingComment);
        return CommentMapper.MAPPER.toCommentDto(updatedComment);
    }

    @Override
    @CachePut(key = "'patchedComment:' + #result.id")
    public CommentDto patchComment(Long id, CommentPostDto commentPostDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment existingComment = optionalComment.orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        if (commentPostDto.getUserId() != null)
            existingComment.setUser(userRepository.getReferenceById(commentPostDto.getUserId()));
        if (commentPostDto.getMovieId() != null)
            existingComment.setMovie(movieRepository.getReferenceById(commentPostDto.getMovieId()));
        if (commentPostDto.getContent() != null)
            existingComment.setContent(commentPostDto.getContent());
        if (commentPostDto.getLikes() != null)
            existingComment.setLikes(commentPostDto.getLikes());
        if (commentPostDto.getDislikes() != null)
            existingComment.setDislikes(commentPostDto.getDislikes());
        if (commentPostDto.getStatus() != null)
            existingComment.setStatus(commentPostDto.getStatus());
        existingComment.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Comment patchedComment = commentRepository.save(existingComment);
        return CommentMapper.MAPPER.toCommentDto(patchedComment);
    }

    @Override
    @CacheEvict(key = "'deletedComment:' + #id")
    public CommentDto deleteCommentByID(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment existingComment = optionalComment.orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        existingComment.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        existingComment.setStatus(CommentStatus.DELETED);
        Comment deletedComment = commentRepository.save(existingComment);
        return CommentMapper.MAPPER.toCommentDto(deletedComment);
    }

    @Override
    public Page<CommentDto> getCommentsByStatus(CommentStatus status, Pageable pageable) {
        return CommentMapper.MAPPER.toCommentDtoPage(commentRepository.findByStatusIs(status, pageable));
    }

    @Override
    public Page<CommentDto> getCommentsSortedBy(Pageable pageable) {
        return CommentMapper.MAPPER.toCommentDtoPage(commentRepository.findAll(pageable));
    }

    @Override
    public Page<CommentDto> searchComments(String value, Pageable pageable) {
        Integer valueNumeric = tryParseInt(value);
        return CommentMapper.MAPPER.toCommentDtoPage(commentRepository.searchComments(value, valueNumeric, pageable));
    }

    private Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
