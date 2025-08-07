package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model;

public record SupportRequest(String employeeName,
                             IssueType issueType,
                             String description) {
}
