package com.miromax.cinema.services.impl;

import com.miromax.cinema.dtos.SessionDto;
import com.miromax.cinema.dtos.SessionPostDto;
import com.miromax.cinema.entities.Session;
import com.miromax.cinema.entities.enums.Status;
import com.miromax.cinema.exceptions.SessionNotFoundException;
import com.miromax.cinema.mappers.SessionMapper;
import com.miromax.cinema.repositories.HallRepository;
import com.miromax.cinema.repositories.MovieRepository;
import com.miromax.cinema.repositories.SessionRepository;
import com.miromax.cinema.services.SessionService;
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
@CacheConfig(cacheNames = "sessions")
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, MovieRepository movieRepository, HallRepository hallRepository) {
        this.sessionRepository = sessionRepository;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Page<SessionDto> getAllSessions(Pageable pageable) {
        return SessionMapper.MAPPER.toSessionDtoPage(sessionRepository.findAll(pageable));
    }

    @Override
    @Cacheable(key = "'session:' + #id")
    public SessionDto findSessionById(Long id) {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        Session session = optionalSession.orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + id));
        return SessionMapper.MAPPER.toSessionDto(session);
    }

    @Override
    public Page<SessionDto> findSessionsByMovieId(Long movieId, Pageable pageable) {
        Page<Session> sessionPage = sessionRepository.findByMovieId(movieId, pageable);
        return SessionMapper.MAPPER.toSessionDtoPage(sessionPage);
    }

    @Override
    public Page<SessionDto> findSessionsByHallId(Long hallId, Pageable pageable) {
        Page<Session> sessionPage = sessionRepository.findByHallId(hallId, pageable);
        return SessionMapper.MAPPER.toSessionDtoPage(sessionPage);
    }

    @Override
    public SessionDto createSession(SessionPostDto sessionPostDto) {
        Session session = SessionMapper.MAPPER.toSessionEntity(sessionPostDto);
        session.setMovie(movieRepository.getReferenceById(sessionPostDto.getMovieId()));
        session.setHall(hallRepository.getReferenceById(sessionPostDto.getHallId()));
        session.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        session.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Session savedSession = sessionRepository.save(session);
        return SessionMapper.MAPPER.toSessionDto(savedSession);
    }

    @Override
    @CachePut(key = "'updatedSession:' + #result.id")
    public SessionDto updateSession(Long id, SessionPostDto sessionPostDto) {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        Session existingSession = optionalSession.orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + id));
        existingSession.setMovie(movieRepository.getReferenceById(sessionPostDto.getMovieId()));
        existingSession.setHall(hallRepository.getReferenceById(sessionPostDto.getHallId()));
        existingSession.setStartTime(sessionPostDto.getStartTime());
        existingSession.setEndTime(sessionPostDto.getEndTime());
        existingSession.setPrices(sessionPostDto.getPrices());
        existingSession.setStatus(sessionPostDto.getStatus());
        existingSession.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Session updatedSession = sessionRepository.save(existingSession);
        return SessionMapper.MAPPER.toSessionDto(updatedSession);
    }

    @Override
    @CachePut(key = "'patchedSession:' + #result.id")
    public SessionDto patchSession(Long id, SessionPostDto sessionPostDto) {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        Session existingSession = optionalSession.orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + id));
        if (sessionPostDto.getMovieId() != null)
            existingSession.setMovie(movieRepository.getReferenceById(sessionPostDto.getMovieId()));
        if (sessionPostDto.getHallId() != null)
            existingSession.setHall(hallRepository.getReferenceById(sessionPostDto.getHallId()));
        if (sessionPostDto.getStartTime() != null)
            existingSession.setStartTime(sessionPostDto.getStartTime());
        if (sessionPostDto.getEndTime() != null)
            existingSession.setEndTime(sessionPostDto.getEndTime());
        if (sessionPostDto.getPrices() != null)
            existingSession.setPrices(sessionPostDto.getPrices());
        if (sessionPostDto.getStatus() != null)
            existingSession.setStatus(sessionPostDto.getStatus());
        existingSession.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Session patchedSession = sessionRepository.save(existingSession);
        return SessionMapper.MAPPER.toSessionDto(patchedSession);
    }

    @Override
    @CacheEvict(key = "'deletedSession:' + #id")
    public SessionDto deleteSession(Long id) {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        Session existingSession = optionalSession.orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + id));
        existingSession.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        existingSession.setStatus(Status.DELETED);
        Session deletedSession = sessionRepository.save(existingSession);
        return SessionMapper.MAPPER.toSessionDto(deletedSession);
    }

    @Override
    public Page<SessionDto> findSessionsByStatus(Status status, Pageable pageable) {
        return SessionMapper.MAPPER.toSessionDtoPage(sessionRepository.findByStatusIs(status, pageable));
    }

    @Override
    public Page<SessionDto> getSessionsSortedBy(Pageable pageable) {
        return SessionMapper.MAPPER.toSessionDtoPage(sessionRepository.findAll(pageable));
    }
}
