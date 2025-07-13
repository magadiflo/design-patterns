package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class PayPalPayment implements PaymentStrategy {

    private static final Map<String, String> DATA_BASE = new HashMap<>();

    private String email;
    private String password;
    private boolean signedIn;

    static {
        DATA_BASE.put("martin@gmail.com", "martin");
        DATA_BASE.put("milagros@gmail.com", "123456");
    }

    @Override
    public void collectPaymentDetails(BufferedReader reader) {
        log.info("=== Autenticación PayPal ===");
        try {
            this.email = this.prompt(reader, "Ingrese su email de usuario:");
            this.password = this.prompt(reader, "Ingrese su contraseña:");
        } catch (IOException exception) {
            log.error("Error al leer entrada del usuario: {}", exception.getMessage());
        }
    }

    @Override
    public boolean validatePaymentDetails() {
        this.signedIn = Objects.equals(this.password, DATA_BASE.get(email));

        if (this.signedIn) {
            log.info("Inicio de sesión exitoso");
        } else {
            log.error("Email o password incorrectos");
        }
        return this.signedIn;
    }

    @Override
    public boolean pay(int paymentAmount) {
        if (this.signedIn) {
            log.info("Pagando S/ {} con PayPal (usuario: {})", paymentAmount, this.email);
            return true;
        }
        log.warn("El usuario no ha iniciado sesión correctamente. No se puede realizar el pago");
        return false;
    }

    private String prompt(BufferedReader reader, String message) throws IOException {
        log.info(message);
        return reader.readLine().trim();
    }
}
