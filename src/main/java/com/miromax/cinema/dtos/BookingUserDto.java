package com.miromax.cinema.dtos;

import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import lombok.Data;

@Data
public class BookingUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private UserStatus status;
}
