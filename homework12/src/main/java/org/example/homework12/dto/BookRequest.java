package org.example.homework12.dto;

public record BookRequest(
        String title,
        String author,
        Integer year,
        String description
) {
}
