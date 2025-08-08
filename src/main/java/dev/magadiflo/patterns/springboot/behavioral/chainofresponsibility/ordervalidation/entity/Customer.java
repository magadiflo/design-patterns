package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una entidad que será mapeada a una
 * tabla en base de datos.
 * <p>
 * Como no estamos trabajando con Spring Data JPA vamos a omitir
 * las anotaciones típicas: @Entity, @Table, @Id, etc.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//@Entity
//@Table(name = "customers")
public class Customer {
    //@Id
    private Long id;
    String name;
    String email;
    Boolean active;
}
