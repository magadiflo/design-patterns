package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ShippingAddress(@NotBlank
                              @Size(min = 5, max = 100)
                              String street,

                              @NotBlank
                              @Size(min = 5, max = 50)
                              String city,

                              @NotBlank
                              @Pattern(regexp = "\\d{5}")
                              String postalCode,

                              @NotBlank
                              String country) {
}
