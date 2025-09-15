package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.cloneable.problem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        MailSender mailSender1 = MailSender.getInstance();
        MailSender mailSender2 = MailSender.getInstance();
        log.info("{}", System.identityHashCode(mailSender1));
        log.info("{}", System.identityHashCode(mailSender2));

        // Código generado de la copia clonada no coincide
        MailSender mailSender3 = (MailSender) mailSender2.clone();
        log.info("{}", System.identityHashCode(mailSender3));
    }
}
