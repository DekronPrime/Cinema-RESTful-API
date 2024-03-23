package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.BookingDto;
import com.miromax.cinema.dtos.BookingPostDto;
import com.miromax.cinema.dtos.BookingSessionSeatsDto;
import com.miromax.cinema.entities.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingMapper MAPPER = Mappers.getMapper(BookingMapper.class);

    Booking toBookingEntity(BookingPostDto bookingPostDto);
    @Mapping(target = "session.id", source = "session.id")
    @Mapping(target = "session.hall.location.cityId", source = "session.hall.location.city.id")
    @Mapping(target = "session.hall.location.cityName", source = "session.hall.location.city.name")
    BookingDto toBookingDto(Booking booking);
    @Mapping(target = "seatRowId", source = "seatRow.id")
    @Mapping(target = "rowNumber", source = "seatRow.number")
    BookingSessionSeatsDto toBookingSessionSeatsDto(Booking booking);

    default Page<BookingDto> toBookingDtoPage(Page<Booking> bookingPage) {
        return bookingPage.map(this::toBookingDto);
    }

    default List<BookingSessionSeatsDto> toBookingSessionSeatsDtoList(List<Booking> bookingList) {
        return bookingList.stream()
                .map(this::toBookingSessionSeatsDto)
                .collect(Collectors.toList());
    }
}