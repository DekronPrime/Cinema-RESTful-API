package com.miromax.cinema.controllers;

import com.miromax.cinema.dtos.BookingDto;
import com.miromax.cinema.dtos.BookingPostDto;
import com.miromax.cinema.entities.enums.BookingStatus;
import com.miromax.cinema.services.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<BookingDto> bookingPage = bookingService.getAllBookings(PageRequest.of(page, size, Sort.by("id")));
        return ResponseEntity.ok(bookingPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findBookingById(id));
    }

    @GetMapping("/by-session")
    public ResponseEntity<List<BookingDto>> getBookingsBySession(
            @RequestParam Long id,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.findBookingsBySessionId(id, PageRequest.of(page, size));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<BookingDto>> getBookingsByUser(
            @RequestParam Long id,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.findBookingsByUserId(id, PageRequest.of(page, size));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }

    @GetMapping("/by-seat-row")
    public ResponseEntity<List<BookingDto>> getBookingsBySeatRow(
            @RequestParam Long id,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.findBookingsBySeatRowId(id, PageRequest.of(page, size));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingPostDto bookingPostDto) {
        BookingDto createdBooking = bookingService.createBooking(bookingPostDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBooking.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(
            @PathVariable Long id,
            @RequestBody BookingPostDto bookingPostDto) {
        BookingDto bookingDto = bookingService.updateBooking(id, bookingPostDto);
        return ResponseEntity.ok(bookingDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDto> patchBooking(
            @PathVariable Long id,
            @RequestBody BookingPostDto bookingPostDto) {
        BookingDto bookingDto = bookingService.patchBooking(id, bookingPostDto);
        return ResponseEntity.ok(bookingDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<BookingDto>> getBookingsByStatus(
            @RequestParam BookingStatus status,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.getBookingsByStatus(status, PageRequest.of(page, size));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }

    @GetMapping("/sort")
    public ResponseEntity<List<BookingDto>> getBookingsSortedBy(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<BookingDto> bookingDtoPage = bookingService.getBookingsSortedBy(PageRequest.of(page, size, Sort.by(direction, sortBy)));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookingDto>> searchBookings(
            @RequestParam String value,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<BookingDto> bookingDtoPage = bookingService.searchBookings(value, PageRequest.of(page, size));
        return ResponseEntity.ok(bookingDtoPage.getContent());
    }
}
