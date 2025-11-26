package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @NotNull
    private int id;

    @PositiveOrZero
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal financing;

    @NotBlank
    @Column(nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
    private String name;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "department")
    private List<Group> faculties = new ArrayList<>();
}
