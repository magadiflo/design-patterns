package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model;

public record Customer(Long id,
                       String name,
                       String email,
                       boolean active) {
}
