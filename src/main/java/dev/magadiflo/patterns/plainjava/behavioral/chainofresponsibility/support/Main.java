package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.client.SupportClient;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.IssueType;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;

public class Main {
    public static void main(String[] args) {
        SupportRequest req1 = new SupportRequest("Carlos", IssueType.PASSWORD, "No puedo acceder al sistema");
        SupportRequest req2 = new SupportRequest("Luisa", IssueType.NETWORK, "Internet lento");
        SupportRequest req3 = new SupportRequest("Andrés", IssueType.HARDWARE, "Mi laptop no enciende");
        // Aplicará el DefaultSupportHandler, ya que ninguno de los definidos en la cadena la podrá procesar
        SupportRequest req4 = new SupportRequest("Milagros", IssueType.SOFTWARE, "Caducó la licencia de Office");

        SupportClient client = new SupportClient();
        client.processRequest(req1);
        client.processRequest(req2);
        client.processRequest(req3);
        client.processRequest(req4);
    }
}
