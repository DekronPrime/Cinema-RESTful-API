package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.CommentDto;
import com.miromax.cinema.dtos.CommentPostDto;
import com.miromax.cinema.entities.enums.CommentStatus;
import com.miromax.cinema.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<CommentDto> commentDtoPage = commentService.getAllComments(PageRequest.of(page, size, Sort.by("id")));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findCommentById(id));
    }

    @GetMapping("/by-movie")
    public ResponseEntity<List<CommentDto>> getCommentsByMovie(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CommentDto> commentDtoPage = commentService.findCommentsByMovieId(id, PageRequest.of(page, size, Sort.by("writtenAt")));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CommentDto> commentDtoPage = commentService.findCommentsByUserId(id, PageRequest.of(page, size, Sort.by("writtenAt")));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentPostDto commentPostDto) {
        CommentDto createdComment = commentService.createComment(commentPostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdComment.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentPostDto commentPostDto) {
        CommentDto commentDto = commentService.updateComment(id, commentPostDto);
        return ResponseEntity.ok(commentDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> patchComment(
            @PathVariable Long id,
            @RequestBody CommentPostDto commentPostDto) {
        CommentDto commentDto = commentService.patchComment(id, commentPostDto);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteCommentByID(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<CommentDto>> getCommentsByStatus(
            @RequestParam CommentStatus status,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<CommentDto> commentDtoPage = commentService.getCommentsByStatus(status, PageRequest.of(page, size));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @GetMapping("/sort")
    public ResponseEntity<List<CommentDto>> getCommentsSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<CommentDto> commentDtoPage = commentService.getCommentsSortedBy(PageRequest.of(page, size, Sort.by(direction, sortBy)));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> searchComments(
            @RequestParam String value,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<CommentDto> commentDtoPage = commentService.searchComments(value, PageRequest.of(page, size));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }
}
