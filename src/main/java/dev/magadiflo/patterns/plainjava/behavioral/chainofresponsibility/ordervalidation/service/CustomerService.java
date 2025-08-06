package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.Customer;

import java.util.Map;

// Simula acceso a base de datos
public class CustomerService {

    private static final Map<Long, Customer> customers = Map.of(
            1L, new Customer(1L, "John Doe", "john@email.com", true),
            2L, new Customer(2L, "Jane Smith", "jane@email.com", false),
            3L, new Customer(3L, "Mike Johnson", "mike@email.com", true)
    );

    public Customer findById(Long id) {
        return customers.get(id);
    }
}
