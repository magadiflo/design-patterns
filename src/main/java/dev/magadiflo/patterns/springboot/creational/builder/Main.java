package dev.magadiflo.patterns.springboot.creational.builder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        User user = User.builder()
                .name("Martín")
                .username("martin")
                .password("123456")
                .age(36)
                .build();
        log.info(user.toString());
    }
}
