package dev.magadiflo.patterns.plainjava.creational.builder.classic.builder;

import dev.magadiflo.patterns.plainjava.creational.builder.classic.product.Report;

public class PDFReportBuilder implements ReportBuilder {

    private final Report report = new Report();

    @Override
    public void buildHeader(String title) {
        this.report.setHeader("=== PDF HEADER: " + title + " ===");
    }

    @Override
    public void buildContent(String data) {
        this.report.setContent("PDF Content: " + data);
    }

    @Override
    public void buildFooter(String author) {
        this.report.setFooter("PDF Footer - Author: " + author);
    }

    @Override
    public Report getResult() {
        return this.report;
    }
}
