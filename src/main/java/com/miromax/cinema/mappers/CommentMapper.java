package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.CommentDto;
import com.miromax.cinema.dtos.CommentPostDto;
import com.miromax.cinema.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

    Comment toCommentEntity(CommentPostDto commentPostDto);
    CommentDto toCommentDto(Comment comment);

    default Page<CommentDto> toCommentDtoPage(Page<Comment> commentPage) {
        return commentPage.map(this::toCommentDto);
    }
}