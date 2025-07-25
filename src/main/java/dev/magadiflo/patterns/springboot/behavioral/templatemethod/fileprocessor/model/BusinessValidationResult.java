package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

import java.util.List;

public record BusinessValidationResult<T extends ParsedRecord>(List<T> validRecords,
                                                               int invalidCount) {
}
