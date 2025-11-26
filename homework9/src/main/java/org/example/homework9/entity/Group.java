package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @NotNull
    private int id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "VARCHAR(100)", unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1)
    @Max(value = 5)
    private int year;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "group")
    private Set<GroupCurator> groupsCurators = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<GroupLecture> groupsLectures = new HashSet<>();
}
