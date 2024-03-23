package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.UserDto;
import com.miromax.cinema.entities.User;
import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import com.miromax.cinema.exceptions.UserNotFoundException;
import com.miromax.cinema.mappers.UserMapper;
import com.miromax.cinema.repositories.UserRepository;
import com.miromax.cinema.services.PasswordHashingService;
import com.miromax.cinema.services.UserService;
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
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordHashingService passwordHashingService;

    public UserServiceImpl(UserRepository userRepository, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return UserMapper.MAPPER.toUserDtoPage(userRepository.findAll(pageable));
    }

    @Override
    @Cacheable(key = "'user:' + #id")
    public UserDto findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return UserMapper.MAPPER.toUserDto(user);
    }

    @Override
    public Page<UserDto> getUsersByRole(UserRole role, Pageable pageable) {
        return UserMapper.MAPPER.toUserDtoPage(userRepository.findByRoleIs(role, pageable));
    }

    @Override
    public Page<UserDto> getUsersByStatus(UserStatus status, Pageable pageable) {
        return UserMapper.MAPPER.toUserDtoPage(userRepository.findByStatusIs(status, pageable));
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        User user = UserMapper.MAPPER.toUserEntity(userDto);
        user.setPasswordHash(passwordHashingService.hashPassword(userDto.getPassword()));
        user.setRegisteredAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        user.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        User savedUser = userRepository.save(user);
        return UserMapper.MAPPER.toUserDto(savedUser);
    }

    @Override
    @CachePut(key = "'updatedUser:' + #result.id")
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        User existingUser = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPasswordHash(passwordHashingService.hashPassword(userDto.getPassword()));
        existingUser.setPhone(userDto.getPhone());
        existingUser.setRole(userDto.getRole());
        existingUser.setAvatarUrl(userDto.getAvatarUrl());
        existingUser.setStatus(userDto.getStatus());
        existingUser.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.MAPPER.toUserDto(updatedUser);
    }

    @Override
    @CachePut(key = "'patchedUser:' + #result.id")
    public UserDto patchUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        User existingUser = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (userDto.getFirstName() != null)
            existingUser.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null)
            existingUser.setLastName(userDto.getLastName());
        if (userDto.getEmail() != null)
            existingUser.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !passwordHashingService.checkPassword(existingUser.getPasswordHash(), userDto.getPassword()))
            existingUser.setPasswordHash(passwordHashingService.hashPassword(userDto.getPassword()));
        if (userDto.getPhone() != null)
            existingUser.setPhone(userDto.getPhone());
        if (userDto.getRole() != null)
            existingUser.setRole(userDto.getRole());
        if (userDto.getAvatarUrl() != null)
            existingUser.setAvatarUrl(userDto.getAvatarUrl());
        if (userDto.getStatus() != null)
            existingUser.setStatus(userDto.getStatus());
        existingUser.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        User patchedUser = userRepository.save(existingUser);
        return UserMapper.MAPPER.toUserDto(patchedUser);
    }

    @Override
    @CacheEvict(key = "'deletedUser:' + #id")
    public UserDto deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User existingUser = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        existingUser.setStatus(UserStatus.DELETED);
        existingUser.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        User deletedUser = userRepository.save(existingUser);
        return UserMapper.MAPPER.toUserDto(deletedUser);
    }

    @Override
    public Page<UserDto> getUsersSortedBy(Pageable pageable) {
        return UserMapper.MAPPER.toUserDtoPage(userRepository.findAll(pageable));
    }

    @Override
    public Page<UserDto> searchUsers(String value, Pageable pageable) {
        return UserMapper.MAPPER.toUserDtoPage(userRepository.searchUsers(value, pageable));
    }
}