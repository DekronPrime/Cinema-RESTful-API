package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.BookingSessionSeatsDto;
import com.miromax.cinema.dtos.SessionDto;
import com.miromax.cinema.dtos.SessionPostDto;
import com.miromax.cinema.entities.enums.Status;
import com.miromax.cinema.services.BookingService;
import com.miromax.cinema.services.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionController {
    private final SessionService sessionService;
    private final BookingService bookingService;

    public SessionController(SessionService sessionService, BookingService bookingService) {
        this.sessionService = sessionService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getAllSessions(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<SessionDto> sessionPage = sessionService.getAllSessions(PageRequest.of(page, size, Sort.by("id")));
        return ResponseEntity.ok(sessionPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.findSessionById(id));
    }

    @GetMapping("/by-movie")
    public ResponseEntity<List<SessionDto>> getSessionsByMovie(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<SessionDto> sessionDtoPage = sessionService.findSessionsByMovieId(id, PageRequest.of(page, size, Sort.by("startTime")));
        return ResponseEntity.ok(sessionDtoPage.getContent());
    }

    @GetMapping("/by-hall")
    public ResponseEntity<List<SessionDto>> getSessionsByHall(
            @RequestParam Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<SessionDto> sessionDtoPage = sessionService.findSessionsByHallId(id, PageRequest.of(page, size, Sort.by("startTime")));
        return ResponseEntity.ok(sessionDtoPage.getContent());
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<SessionDto>> getSessionsByStatus(
            @RequestParam Status status,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<SessionDto> sessionPage = sessionService.findSessionsByStatus(status, PageRequest.of(page, size, Sort.by("id")));
        return ResponseEntity.ok(sessionPage.getContent());
    }

    @GetMapping("/sort")
    public ResponseEntity<List<SessionDto>> getSessionsSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<SessionDto> sessionDtoPage = sessionService.getSessionsSortedBy(PageRequest.of(page, size, Sort.by(direction, sortBy)));
        return ResponseEntity.ok(sessionDtoPage.getContent());
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionPostDto sessionPostDto) {
        SessionDto createdSession = sessionService.createSession(sessionPostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSession.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdSession);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionDto> updateSession(
            @PathVariable Long id,
            @RequestBody SessionPostDto sessionPostDto) {
        SessionDto sessionDto = sessionService.updateSession(id, sessionPostDto);
        return ResponseEntity.ok(sessionDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SessionDto> patchSession(
            @PathVariable Long id,
            @RequestBody SessionPostDto sessionPostDto) {
        SessionDto sessionDto = sessionService.patchSession(id, sessionPostDto);
        return ResponseEntity.ok(sessionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<BookingSessionSeatsDto>> getAllBookingsFromSession(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findBookingsBySessionId(id));
    }
}
