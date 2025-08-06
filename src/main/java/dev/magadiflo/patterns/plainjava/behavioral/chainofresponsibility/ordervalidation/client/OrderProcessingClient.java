package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.client;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service.AddressService;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service.CustomerService;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service.ProductService;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler.OrderValidationHandler;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl.AddressValidationHandler;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl.BusinessHoursValidatorHandler;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl.CustomerValidationHandler;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl.StockValidationHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderProcessingClient {

    // Method principal que inicia el procesamiento del pedido
    public ValidationResult processOrder(PurchaseOrder order) {
        log.info("==== Procesando orden ====");
        log.info("Customer ID: {}", order.customerId());
        log.info("Items: {}", order.items().size());
        log.info("Total amount: S/ {}", String.format("%.2f", order.getTotalAmount()));
        log.info("Destination: {}", order.shippingAddress().city());
        log.info("===========================");

        // Construcción e invocación de la cadena
        OrderValidationHandler handlerChain = this.buildValidationChain();
        ValidationResult result = handlerChain.handle(order);

        if (result.isValid()) {
            log.info("Orden procesada correctamente.");
            log.info("El pedido ha sido registrado y está listo para su preparación.");
            //Aquí iría la lógica para persistir en BD, enviar notificaciones, etc.
        } else {
            log.error("Error procesando la orden.");
            log.error("Código de error: {}", result.errorCode());
            log.error("Detalle: {}", result.errorMessage());
            //Aquí irían los registros, métricas, notificaciones de errores, etc.
        }
        return result;
    }

    /**
     * Configura la cadena de validación: Aquí es donde el cliente construye la cadena de validación
     * Nota: El orden es fundamental en las validaciones empresariales.
     */
    private OrderValidationHandler buildValidationChain() {
        // Instancia de handlers concretos
        OrderValidationHandler customerValidator = new CustomerValidationHandler(new CustomerService());
        OrderValidationHandler stockValidator = new StockValidationHandler(new ProductService());
        OrderValidationHandler addressValidator = new AddressValidationHandler(new AddressService());
        OrderValidationHandler hoursValidator = new BusinessHoursValidatorHandler();

        // Encadenamiento de responsabilidades
        customerValidator
                .setNext(stockValidator)
                .setNext(addressValidator)
                .setNext(hoursValidator);

        return customerValidator; // Primer eslabón de la cadena
    }

}
