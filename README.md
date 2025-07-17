# 🧠 Design Patterns en Java y Spring Boot

**Referencias**

- [🌱 PlantUML](https://plantuml.com/es/)
- [Implementing the Strategy Design pattern in Spring Boot](https://medium.com/codex/implementing-the-strategy-design-pattern-in-spring-boot-df3adb9ceb4a)
- [Refactoring.guru](https://refactoring.guru/es/design-patterns/java)
- [JavaTechOnline](https://javatechonline.com/java-design-patterns-java/)

---

Este repositorio contiene una colección organizada de patrones de diseño implementados en dos contextos:

- `Java puro`: Implementaciones `sin frameworks`, enfocadas en la lógica esencial del patrón.
- `Spring Boot`: Implementaciones aplicadas a escenarios reales usando el ecosistema de `Spring`.

El objetivo es servir como una fuente de consulta práctica, con ejemplos claros, múltiples variantes por patrón, y
documentación complementaria.

## 📂 Estructura del repositorio

Cada patrón está clasificado por su tipo:

- **Creacional (creational)**
- **Estructural (structural)**
- **De comportamiento (behavioral)**

Y cada tipo está implementado en dos formas:

| Contexto      | Descripción                                                                                        |
|---------------|----------------------------------------------------------------------------------------------------|
| `plainjava/`  | Implementaciones en Java puro, sin dependencias externas.                                          |
| `springboot/` | Implementaciones usando Spring Boot, aprovechando inyección de dependencias, eventos, scopes, etc. |

## 23 patrones de diseño de GoF (Gang Of Four)

| `Creational`     | `Structural` | `Behavioral`            |
|------------------|--------------|-------------------------|
| Singleton        | Adapter      | Chain of Responsibility |
| Factory Method   | Bridge       | Command                 |
| Abstract Factory | Composite    | Interpreter             |
| Builder          | Decorator    | Iterator                |
| Prototype        | Facade       | Mediator                |
|                  | Flyweight    | Memento                 |
|                  | Proxy        | Observer                |
|                  |              | State                   |
|                  |              | Strategy                |
|                  |              | Template Method         |
|                  |              | Visitor                 |

