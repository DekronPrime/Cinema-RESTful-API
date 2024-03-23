package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Location;
import com.miromax.cinema.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByCityId(Long cityId);
    @Query("SELECT l FROM Location l WHERE " +
            "LOWER(l.shoppingMall) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(l.address) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
            "LOWER(l.details) LIKE LOWER(CONCAT('%', :value, '%'))")
    List<Location> searchLocations(@Param("value") String value);
}
