package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model;

public record Product(Long id,
                      String name,
                      double price,
                      int availableStock) {
}
