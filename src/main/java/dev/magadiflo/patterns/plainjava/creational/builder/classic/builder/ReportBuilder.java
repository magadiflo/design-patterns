package dev.magadiflo.patterns.plainjava.creational.builder.classic.builder;

import dev.magadiflo.patterns.plainjava.creational.builder.classic.product.Report;

public interface ReportBuilder {
    void buildHeader(String title);

    void buildContent(String data);

    void buildFooter(String author);

    Report getResult();
}
