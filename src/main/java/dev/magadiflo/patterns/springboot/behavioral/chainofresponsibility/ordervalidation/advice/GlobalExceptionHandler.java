package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.advice;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.BusinessValidationException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.CustomerNotFoundException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ProblemDetail> handleException(BusinessValidationException ex) {
        log.error("Error de validación de negocio: {}", ex.getMessage());
        HttpStatus status = this.getStatus(ex);
        ProblemDetail problemDetail = this.build(status, ex, detail -> {
            detail.setTitle("Problemas en la validación de lógica de negocio");
        });
        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex) {
        log.error("Error de validación de datos: {}", ex.getMessage());
        Map<String, List<String>> errors = this.extractValidationErrors(ex);
        ProblemDetail problemDetail = this.build(HttpStatus.BAD_REQUEST, ex, detail -> {
            detail.setTitle("Datos de entrada inválidos");
            detail.setProperty("errors", errors);
        });
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
        log.error("Error genérico: {}", ex.getMessage());
        ProblemDetail problemDetail = this.build(HttpStatus.INTERNAL_SERVER_ERROR, ex, detail -> {
            detail.setTitle("Se ha producido un error genérico");
        });
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    private Map<String, List<String>> extractValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
    }

    private HttpStatus getStatus(BusinessValidationException ex) {
        return switch (ex) {
            case CustomerNotFoundException e -> HttpStatus.NOT_FOUND;
            case ProductNotFoundException e -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> detailConsumer) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        detailConsumer.accept(problemDetail);
        return problemDetail;
    }

}
