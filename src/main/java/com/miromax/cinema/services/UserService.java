package com.miromax.cinema.services;

import com.miromax.cinema.dtos.UserDto;
import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Page<UserDto> getAllUsers(Pageable pageable);

    Page<UserDto> getUsersByRole(UserRole role, Pageable pageable);

    Page<UserDto> getUsersByStatus(UserStatus status, Pageable pageable);

    UserDto findUserById(Long id);

    UserDto postUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    UserDto patchUser(Long id, UserDto userDto);

    UserDto deleteUser(Long id);

    Page<UserDto> getUsersSortedBy(Pageable pageable);

    Page<UserDto> searchUsers(String value, Pageable pageable);
}