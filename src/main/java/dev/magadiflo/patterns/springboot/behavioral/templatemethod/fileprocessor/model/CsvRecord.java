package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

import java.time.LocalDate;

public record CsvRecord(String id,
                        String firstName,
                        String lastName,
                        String email,
                        LocalDate registrationDate) implements ParsedRecord {
}
