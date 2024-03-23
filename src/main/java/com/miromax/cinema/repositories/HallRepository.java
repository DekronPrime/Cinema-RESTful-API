package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Hall;
import com.miromax.cinema.entities.enums.HallType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findByLocationId(Long locationId);
    List<Hall> findByNameIs(String name);
    List<Hall> findByTypeIs(HallType type);
}
