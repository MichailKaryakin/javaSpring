package org.example.homework45.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class BulkAddResponse {
    private Integer added;
    private Integer failed;
    private List<ResponseEntity<?>> errors;
}
