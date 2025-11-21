package org.example.homework8.entity.oneToOne;

import jakarta.persistence.*;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne
    @JoinColumn(
            name = "seat_id",
            referencedColumnName = "id",
            unique = true
    )
    private Seat seat;
}
