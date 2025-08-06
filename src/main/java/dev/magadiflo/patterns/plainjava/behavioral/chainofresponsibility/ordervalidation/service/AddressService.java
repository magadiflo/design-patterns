package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.ShippingAddress;

import java.util.Objects;
import java.util.Set;

// Simula acceso a base de datos
public class AddressService {
    private static final Set<String> validCountries = Set.of("USA", "CANADA", "MEXICO", "PERU", "COLOMBIA");

    public boolean isValidAddress(ShippingAddress address) {
        return Objects.nonNull(address.street()) && !address.street().isBlank() &&
               Objects.nonNull(address.city()) && !address.city().isBlank() &&
               Objects.nonNull(address.postalCode()) && !address.postalCode().isBlank() &&
               validCountries.contains(address.country().toUpperCase());
    }
}
