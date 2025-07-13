package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CreditCard {
    @Getter
    private final String number;
    @Getter
    private final String expiry;
    @Getter
    private final String cvv;
    @Getter
    @Setter
    private int amount = 100_000; //Simula saldo de la tarjeta

    public CreditCard(String number, String expiry, String cvv) {
        this.number = number;
        this.expiry = expiry;
        this.cvv = cvv;
    }

}
