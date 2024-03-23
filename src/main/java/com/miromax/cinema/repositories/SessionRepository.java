package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Session;
import com.miromax.cinema.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Page<Session> findByMovieId(Long movieId, Pageable pageable);
    Page<Session> findByHallId(Long hallId, Pageable pageable);
    Page<Session> findByStatusIs(Status status, Pageable pageable);
}
