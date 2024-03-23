package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.BookingDto;
import com.miromax.cinema.dtos.BookingPostDto;
import com.miromax.cinema.dtos.BookingSessionSeatsDto;
import com.miromax.cinema.entities.Booking;
import com.miromax.cinema.entities.enums.BookingStatus;
import com.miromax.cinema.exceptions.BookingNotFoundException;
import com.miromax.cinema.mappers.BookingMapper;
import com.miromax.cinema.repositories.BookingRepository;
import com.miromax.cinema.repositories.SeatRowRepository;
import com.miromax.cinema.repositories.SessionRepository;
import com.miromax.cinema.repositories.UserRepository;
import com.miromax.cinema.services.BookingService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "bookings")
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SeatRowRepository seatRowRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, SessionRepository sessionRepository, UserRepository userRepository, SeatRowRepository seatRowRepository) {
        this.bookingRepository = bookingRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.seatRowRepository = seatRowRepository;
    }

    @Override
    @Cacheable(key = "'pageable:' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<BookingDto> getAllBookings(Pageable pageable) {
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);
        return BookingMapper.MAPPER.toBookingDtoPage(bookingPage);
    }

    @Override
    @Cacheable(key = "'booking:' + #id")
    public BookingDto findBookingById(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        Booking booking = optionalBooking.orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        return BookingMapper.MAPPER.toBookingDto(booking);
    }

    @Override
    public List<BookingSessionSeatsDto> findBookingsBySessionId(Long sessionId) {
        List<Booking> bookingList = bookingRepository.findBySessionId(sessionId);
        return BookingMapper.MAPPER.toBookingSessionSeatsDtoList(bookingList);
    }

    @Override
    public Page<BookingDto> findBookingsBySessionId(Long sessionId, Pageable pageable) {
        Page<Booking> bookingPage = bookingRepository.findBySessionId(sessionId, pageable);
        return BookingMapper.MAPPER.toBookingDtoPage(bookingPage);
    }

    @Override
    public Page<BookingDto> findBookingsByUserId(Long userId, Pageable pageable) {
        Page<Booking> bookingPage = bookingRepository.findByUserId(userId, pageable);
        return BookingMapper.MAPPER.toBookingDtoPage(bookingPage);
    }

    @Override
    public Page<BookingDto> findBookingsBySeatRowId(Long seatRowId, Pageable pageable) {
        Page<Booking> bookingPage = bookingRepository.findBySeatRowId(seatRowId, pageable);
        return BookingMapper.MAPPER.toBookingDtoPage(bookingPage);
    }

    @Override
    public Page<BookingDto> getBookingsByStatus(BookingStatus status, Pageable pageable) {
        return BookingMapper.MAPPER.toBookingDtoPage(bookingRepository.findByStatusIs(status, pageable));
    }

    @Override
    public BookingDto createBooking(BookingPostDto bookingPostDto) {
        Booking booking = BookingMapper.MAPPER.toBookingEntity(bookingPostDto);
        booking.setSession(sessionRepository.getReferenceById(bookingPostDto.getSessionId()));
        booking.setUser(userRepository.getReferenceById(bookingPostDto.getUserId()));
        booking.setSeatRow(seatRowRepository.getReferenceById(bookingPostDto.getSeatRowId()));
        booking.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        booking.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.MAPPER.toBookingDto(savedBooking);
    }

    @Override
    @CachePut(key = "'updatedBooking:' + #result.id")
    public BookingDto updateBooking(Long id, BookingPostDto bookingPostDto) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        Booking existingBooking = optionalBooking.orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        existingBooking.setSession(sessionRepository.getReferenceById(bookingPostDto.getSessionId()));
        existingBooking.setUser(userRepository.getReferenceById(bookingPostDto.getUserId()));
        existingBooking.setSeatRow(seatRowRepository.getReferenceById(bookingPostDto.getSeatRowId()));
        existingBooking.setSeatNumber(bookingPostDto.getSeatNumber());
        existingBooking.setPrice(bookingPostDto.getPrice());
        existingBooking.setStatus(bookingPostDto.getStatus());
        existingBooking.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Booking updatedBooking = bookingRepository.save(existingBooking);
        return BookingMapper.MAPPER.toBookingDto(updatedBooking);
    }

    @Override
    @CachePut(key = "'patchedBooking:' + #result.id")
    public BookingDto patchBooking(Long id, BookingPostDto bookingPostDto) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        Booking existingBooking = optionalBooking.orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        if (bookingPostDto.getSessionId() != null)
            existingBooking.setSession(sessionRepository.getReferenceById(bookingPostDto.getSessionId()));
        if (bookingPostDto.getUserId() != null)
            existingBooking.setUser(userRepository.getReferenceById(bookingPostDto.getUserId()));
        if (bookingPostDto.getSeatRowId() != null)
            existingBooking.setSeatRow(seatRowRepository.getReferenceById(bookingPostDto.getSeatRowId()));
        if (bookingPostDto.getSeatNumber() != null)
            existingBooking.setSeatNumber(bookingPostDto.getSeatNumber());
        if (bookingPostDto.getPrice() != null)
            existingBooking.setPrice(bookingPostDto.getPrice());
        if (bookingPostDto.getStatus() != null)
            existingBooking.setStatus(bookingPostDto.getStatus());
        existingBooking.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Booking patchedBooking = bookingRepository.save(existingBooking);
        return BookingMapper.MAPPER.toBookingDto(patchedBooking);
    }

    @Override
    @CacheEvict(key = "'deletedBooking:' + #id")
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Page<BookingDto> getBookingsSortedBy(Pageable pageable) {
        return BookingMapper.MAPPER.toBookingDtoPage(bookingRepository.findAll(pageable));
    }

    @Override
    public Page<BookingDto> searchBookings(String value, Pageable pageable) {
        return BookingMapper.MAPPER.toBookingDtoPage(bookingRepository.searchBookings(value, pageable));
    }
}
