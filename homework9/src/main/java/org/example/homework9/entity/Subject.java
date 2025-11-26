package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @NotNull
    private int id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
    private String name;

    @OneToOne(mappedBy = "subject")
    private Lecture lecture;
}
