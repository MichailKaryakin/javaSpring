package org.example.homework8.entity.oneToMany;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Guitarist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @OneToMany(mappedBy = "guitarist")
    private List<Guitar> guitars = new ArrayList<Guitar>();
}
