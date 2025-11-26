package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @NotNull
    private int id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String lectureRoom;

    @OneToOne
    @NotNull
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @OneToOne
    @NotNull
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher teacher;

    @OneToMany(mappedBy = "lecture")
    private Set<GroupLecture> groupsLectures = new HashSet<>();
}
