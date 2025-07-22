package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto;

import java.time.LocalDateTime;

public record TxtRecord(String id,
                        String accountNumber,
                        double amount,
                        LocalDateTime transactionDate,
                        String description) implements ParsedRecord {
}
