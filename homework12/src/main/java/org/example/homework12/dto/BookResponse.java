package org.example.homework12.dto;

public record BookResponse(
        Long id,
        String title,
        String author,
        Integer year,
        String description
) {
}
