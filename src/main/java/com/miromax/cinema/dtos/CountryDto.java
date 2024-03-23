package com.miromax.cinema.dtos;

import lombok.Data;

@Data
public class CountryDto {
    private Long id;
    private String code;
    private String name;
    private String flagUrl;
}