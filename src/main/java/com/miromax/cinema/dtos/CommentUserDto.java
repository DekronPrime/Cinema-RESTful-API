package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import lombok.Data;

@Data
public class CommentUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String avatarUrl;
    private UserStatus status;
}