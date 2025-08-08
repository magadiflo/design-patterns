package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.ShippingAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService {
    private final Set<String> validCountries = Set.of("USA", "CANADA", "MEXICO", "PERU", "COLOMBIA");

    public boolean isValidAddress(ShippingAddress address) {
        return Objects.nonNull(address.street()) && !address.street().isBlank() &&
               Objects.nonNull(address.city()) && !address.city().isBlank() &&
               Objects.nonNull(address.postalCode()) && !address.postalCode().isBlank() &&
               this.validCountries.contains(address.country().toUpperCase());
    }
}
