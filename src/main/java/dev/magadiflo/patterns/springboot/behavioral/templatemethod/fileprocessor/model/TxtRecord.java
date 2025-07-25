package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

import java.time.LocalDateTime;

public record TxtRecord(String id,
                        String accountNumber,
                        double amount,
                        LocalDateTime transactionDate,
                        String description) implements ParsedRecord {
}
