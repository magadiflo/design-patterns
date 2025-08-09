package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.config;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler.AddressValidationHandler;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler.BusinessHoursValidationHandler;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler.CustomerValidationHandler;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler.OrderValidationHandler;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler.StockValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OrderValidationConfig {
    @Bean(value = "orderValidationChain")
    public OrderValidationHandler orderValidationHandler(CustomerValidationHandler customerValidator,
                                                         StockValidationHandler stockValidator,
                                                         AddressValidationHandler addressValidator,
                                                         BusinessHoursValidationHandler hoursValidator) {
        log.info("Configurando cadena de validación de órdenes...");
        customerValidator
                .setNext(stockValidator)
                .setNext(addressValidator)
                .setNext(hoursValidator);

        return customerValidator;
    }
}
