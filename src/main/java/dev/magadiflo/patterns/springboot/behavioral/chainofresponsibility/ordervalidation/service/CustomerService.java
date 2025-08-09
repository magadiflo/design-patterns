package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
    private final Map<Long, Customer> customers = Map.of(
            1L, new Customer(1L, "John Doe", "john@email.com", true),
            2L, new Customer(2L, "Jane Smith", "jane@email.com", false),
            3L, new Customer(3L, "Mike Johnson", "mike@email.com", true)
    );

    public Customer findById(Long customerId) {
        return this.customers.get(customerId);
    }
}
