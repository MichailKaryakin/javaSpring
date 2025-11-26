package org.example.homework10.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "books",
        indexes = {
                @Index(name = "idx_book_isbn", columnList = "isbn"),
                @Index(name = "idx_book_published_year", columnList = "publishedYear")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank
    private String title;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    private String isbn;

    @Min(0)
    private Integer publishedYear;

    @PositiveOrZero
    private Double price;

    @Positive
    private Integer pages;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
