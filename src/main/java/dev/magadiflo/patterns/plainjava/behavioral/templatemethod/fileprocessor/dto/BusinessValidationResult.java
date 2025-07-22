package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto;

import java.util.List;

public record BusinessValidationResult<T extends ParsedRecord>(List<T> validRecords,
                                                               int invalidCount) {
}
