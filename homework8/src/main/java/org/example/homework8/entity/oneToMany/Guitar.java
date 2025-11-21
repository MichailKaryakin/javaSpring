package org.example.homework8.entity.oneToMany;

import jakarta.persistence.*;

@Entity
public class Guitar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @ManyToOne
    @JoinColumn(name = "guitarist_id", referencedColumnName = "id")
    private Guitarist guitarist;
}
