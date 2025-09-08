package dev.magadiflo.patterns.plainjava.creational.builder.modern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) {
        User user = User.builder()
                .name("Milagros")
                .username("mila")
                .password("123456")
                .age(19)
                .build();
        log.info(user.toString());
    }
}
