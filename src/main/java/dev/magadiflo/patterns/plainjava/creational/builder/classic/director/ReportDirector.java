package dev.magadiflo.patterns.plainjava.creational.builder.classic.director;

import dev.magadiflo.patterns.plainjava.creational.builder.classic.builder.ReportBuilder;
import dev.magadiflo.patterns.plainjava.creational.builder.classic.product.Report;

public class ReportDirector {

    private ReportBuilder builder;

    public ReportDirector(ReportBuilder builder) {
        this.builder = builder;
    }

    public void changeBuilder(ReportBuilder builder) {
        this.builder = builder;
    }

    public Report build(String title, String data, String author) {
        this.builder.buildHeader(title);
        this.builder.buildContent(data);
        this.builder.buildFooter(author);
        return this.builder.getResult();
    }
}
