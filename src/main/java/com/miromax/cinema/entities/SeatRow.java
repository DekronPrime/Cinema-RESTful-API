package com.miromax.cinema.entities;

import com.miromax.cinema.entities.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat_rows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeatRow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_rows_seq")
    @SequenceGenerator(name = "seat_rows_seq", sequenceName = "seat_rows_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(name = "number", nullable = false)
    private Integer number;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType type;
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
    @Column(name = "price", nullable = false)
    private Integer price;
}