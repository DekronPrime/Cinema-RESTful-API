package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    UserRole role;
    String avatarUrl;
    LocalDateTime registeredAt;
    LocalDateTime updatedAt;
    UserStatus status;
}