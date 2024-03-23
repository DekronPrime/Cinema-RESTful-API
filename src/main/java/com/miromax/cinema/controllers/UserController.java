package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.BookingDto;
import com.miromax.cinema.dtos.CommentDto;
import com.miromax.cinema.dtos.UserDto;
import com.miromax.cinema.entities.enums.UserRole;
import com.miromax.cinema.entities.enums.UserStatus;
import com.miromax.cinema.services.BookingService;
import com.miromax.cinema.services.CommentService;
import com.miromax.cinema.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final CommentService commentService;
    private final BookingService bookingService;

    public UserController(UserService userService, CommentService commentService, BookingService bookingService) {
        this.userService = userService;
        this.commentService = commentService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<UserDto> userPage = userService.getAllUsers(PageRequest.of(page, size, Sort.by("id")));
        return ResponseEntity.ok(userPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<UserDto>> getUsersByRole(
            @RequestParam UserRole role,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<UserDto> userDtoPage = userService.getUsersByRole(role, PageRequest.of(page, size));
        return ResponseEntity.ok(userDtoPage.getContent());
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<UserDto>> getUsersByStatus(
            @RequestParam UserStatus status,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<UserDto> userDtoPage = userService.getUsersByStatus(status, PageRequest.of(page, size));
        return ResponseEntity.ok(userDtoPage.getContent());
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.postUser(userDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        UserDto patchedUser = userService.patchUser(id, userDto);
        return ResponseEntity.ok(patchedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sort")
    public ResponseEntity<List<UserDto>> getUsersSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<UserDto> userDtoPage = userService.getUsersSortedBy(PageRequest.of(page, size, Sort.by(direction, sortBy)));
        return ResponseEntity.ok(userDtoPage.getContent());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(
            @RequestParam String value,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<UserDto> userDtoPage = userService.searchUsers(value, PageRequest.of(page, size));
        return ResponseEntity.ok(userDtoPage.getContent());
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CommentDto> commentDtoPage = commentService.findCommentsByUserId(id, PageRequest.of(page, size, Sort.by("writtenAt")));
        return ResponseEntity.ok(commentDtoPage.getContent());
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsByUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.findBookingsByUserId(id, PageRequest.of(page, size, Sort.by("createdAt")));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }
}