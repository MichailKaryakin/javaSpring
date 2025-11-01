package org.example.homework45.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class BulkAddResponse {
    public Integer added;
    public Integer failed;
    public List<ResponseEntity<?>> errors;
}
