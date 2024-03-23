package com.miromax.cinema.repositories;

import com.miromax.cinema.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
