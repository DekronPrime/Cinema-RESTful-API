package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.BookingDto;
import com.miromax.cinema.dtos.HallDto;
import com.miromax.cinema.dtos.SeatRowDto;
import com.miromax.cinema.dtos.SeatRowPostDto;
import com.miromax.cinema.entities.enums.HallType;
import com.miromax.cinema.entities.enums.SeatType;
import com.miromax.cinema.services.BookingService;
import com.miromax.cinema.services.SeatRowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/seat-rows")
public class SeatRowController {
    private final SeatRowService seatRowService;
    private final BookingService bookingService;

    public SeatRowController(SeatRowService seatRowService, BookingService bookingService) {
        this.seatRowService = seatRowService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<SeatRowDto>> getAllSeatRows() {
        return ResponseEntity.ok(seatRowService.getAllSeatRows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatRowDto> getSeatRowById(@PathVariable Long id) {
        return ResponseEntity.ok(seatRowService.findSeatRowById(id));
    }

    @GetMapping("/by-hall")
    public ResponseEntity<List<SeatRowDto>> getSeatRowsByHall(@RequestParam Long id) {
        return ResponseEntity.ok(seatRowService.findSeatRowsByHallId(id));
    }

    @GetMapping("/by-row")
    public ResponseEntity<List<SeatRowDto>> getSeatRowsByRowNumber(@RequestParam Integer number) {
        return ResponseEntity.ok(seatRowService.findSeatRowsByRowNumber(number));
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<SeatRowDto>> getSeatRowsByType(@RequestParam SeatType type) {
        return ResponseEntity.ok(seatRowService.findSeatRowsByType(type));
    }

    @GetMapping("/by-price")
    public ResponseEntity<List<SeatRowDto>> getSeatRowsByPrice(@RequestParam Integer price) {
        return ResponseEntity.ok(seatRowService.findSeatRowsByPrice(price));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<SeatRowDto>> getSeatRowsSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(seatRowService.getSeatRowsSortedBy(sortBy, sortDirection));
    }

    @PostMapping
    public ResponseEntity<SeatRowDto> createSeatRow(@RequestBody SeatRowPostDto seatRowPostDto) {
        SeatRowDto createdSeatRow = seatRowService.createSeatRow(seatRowPostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSeatRow.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdSeatRow);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatRowDto> updateSeatRow(
            @PathVariable Long id,
            @RequestBody SeatRowPostDto seatRowPostDto) {
        return ResponseEntity.ok(seatRowService.updateSeatRow(id, seatRowPostDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SeatRowDto> patchSeatRow(
            @PathVariable Long id,
            @RequestBody SeatRowPostDto seatRowPostDto) {
        return ResponseEntity.ok(seatRowService.patchSeatRow(id, seatRowPostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeatRow(@PathVariable Long id) {
        seatRowService.deleteSeatRowById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsBySeatRow(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.findBookingsBySeatRowId(id, PageRequest.of(page, size, Sort.by("createdAt")));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }
}
