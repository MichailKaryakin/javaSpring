package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @NotNull
    private int id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String surname;

    @PositiveOrZero
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal salary;

    @OneToOne(mappedBy = "teacher")
    private Lecture lecture;
}
