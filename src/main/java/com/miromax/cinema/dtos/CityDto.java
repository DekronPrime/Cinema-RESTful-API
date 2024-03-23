package com.miromax.cinema.dtos;

import lombok.Data;

@Data
public class CityDto {
    private Long id;
    private String name;
    private Long countryId;
}
