package com.miromax.cinema.services;

import com.miromax.cinema.dtos.*;
import com.miromax.cinema.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SessionService {
    Page<SessionDto> getAllSessions(Pageable pageable);
    SessionDto findSessionById(Long id);
    Page<SessionDto> findSessionsByMovieId(Long movieId, Pageable pageable);
    Page<SessionDto> findSessionsByHallId(Long hallId, Pageable pageable);
    SessionDto createSession(SessionPostDto sessionPostDto);
    SessionDto updateSession(Long id, SessionPostDto sessionPostDto);
    SessionDto patchSession(Long id, SessionPostDto sessionPostDto);
    SessionDto deleteSession(Long id);
    Page<SessionDto> findSessionsByStatus(Status status, Pageable pageable);
    Page<SessionDto> getSessionsSortedBy(Pageable pageable);
}
