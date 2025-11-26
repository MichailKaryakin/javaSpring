package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "curators")
public class Curator {
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

    @OneToMany(mappedBy = "curator")
    private Set<GroupCurator> groupCurators = new HashSet<>();
}
