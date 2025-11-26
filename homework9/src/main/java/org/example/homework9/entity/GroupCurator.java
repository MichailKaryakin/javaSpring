package org.example.homework9.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "groups_curators")
public class GroupCurator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @NotNull
    private int id;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
