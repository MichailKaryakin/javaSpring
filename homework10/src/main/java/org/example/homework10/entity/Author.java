package org.example.homework10.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String firstName;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    @Email
    @NotBlank
    private String email;

    @Min(0)
    private Integer age;

    @Column(length = 60)
    private String country;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
