package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto;

import java.time.LocalDate;

public record CsvRecord(String id,
                        String firstName,
                        String lastName,
                        String email,
                        LocalDate registrationDate) implements ParsedRecord {
}
