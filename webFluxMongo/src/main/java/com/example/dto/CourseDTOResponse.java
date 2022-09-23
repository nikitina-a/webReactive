package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDTOResponse {
    private String id;
    private String name;
    private List<String> students;
    private boolean isClosed;
    private LocalDate createdOn;
    private LocalDate updatedOn;
}