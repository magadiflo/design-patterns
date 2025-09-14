package dev.magadiflo.patterns.springboot.creational.builder;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class User {

    private String name;
    private String username;
    private String password;
    private int age;

}
