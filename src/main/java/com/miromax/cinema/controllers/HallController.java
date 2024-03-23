package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.HallPostDto;
import com.miromax.cinema.dtos.SeatRowDto;
import com.miromax.cinema.dtos.SessionDto;
import com.miromax.cinema.entities.enums.HallType;
import com.miromax.cinema.services.HallService;
import com.miromax.cinema.services.SeatRowService;
import com.miromax.cinema.services.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/halls")
public class HallController {
    private final HallService hallService;
    private final SeatRowService seatRowService;
    private final SessionService sessionService;

    public HallController(HallService hallService, SeatRowService seatRowService, SessionService sessionService) {
        this.hallService = hallService;
        this.seatRowService = seatRowService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<HallDto>> getAllHalls() {
        return ResponseEntity.ok(hallService.getAllHalls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getHallById(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.findHallById(id));
    }

    @GetMapping("/by-location")
    public ResponseEntity<List<HallDto>> getHallsByLocation(@RequestParam Long id) {
        return ResponseEntity.ok(hallService.findHallsByLocationId(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<HallDto>> getHallsByName(@RequestParam String value) {
        return ResponseEntity.ok(hallService.findHallsByName(value));
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<HallDto>> getHallsByType(@RequestParam HallType type) {
        return ResponseEntity.ok(hallService.findHallsByType(type));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<HallDto>> getHallsSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(hallService.findHallsSortedBy(sortBy, sortDirection));
    }

    @PostMapping
    public ResponseEntity<HallDto> createHall(@RequestBody HallPostDto hallPostDto) {
        HallDto createdHall = hallService.createHall(hallPostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdHall.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdHall);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallDto> updateHall(
            @PathVariable Long id,
            @RequestBody HallPostDto hallPostDto) {
        return ResponseEntity.ok(hallService.updateHall(id, hallPostDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HallDto> patchHall(
            @PathVariable Long id,
            @RequestBody HallPostDto hallPostDto) {
        return ResponseEntity.ok(hallService.patchHall(id, hallPostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.deleteHallById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/seat-rows")
    public ResponseEntity<List<SeatRowDto>> getSeatRowsByHall(@PathVariable Long id) {
        return ResponseEntity.ok(seatRowService.findSeatRowsByHallId(id));
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<List<SessionDto>> getSessionsByHall(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<SessionDto> sessionDtoPage = sessionService.findSessionsByHallId(id, PageRequest.of(page,size));
        return ResponseEntity.ok(sessionDtoPage.getContent());
    }
}
