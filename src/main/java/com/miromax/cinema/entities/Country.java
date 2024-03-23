package com.miromax.cinema.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_seq")
    @SequenceGenerator(name = "countries_seq", sequenceName = "countries_seq", allocationSize = 1)
    private Long id;
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "flag_url", nullable = false)
    private String flagUrl;

    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Director> directors = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Actor> actors = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<City> cities = new ArrayList<>();
}