package org.example.homework8.entity.oneToOne;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    @OneToOne(mappedBy = "seat")
    private Passenger passenger;
}
