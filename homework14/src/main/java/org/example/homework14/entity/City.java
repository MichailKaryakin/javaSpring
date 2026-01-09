package org.example.homework14.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Stating city name is required")
    private String name;

    @Min(value = 1, message = "Area can not be zero")
    private double area;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
