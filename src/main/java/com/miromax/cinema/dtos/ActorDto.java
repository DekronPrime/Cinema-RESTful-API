package com.miromax.cinema.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miromax.cinema.entities.enums.ActorPopularity;
import lombok.Data;

@Data
public class ActorDto {
    private Long id;
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String middleName;
    private String lastName;
    private ActorPopularity popularity;
}