package dev.magadiflo.patterns.plainjava.creational.builder.classic.builder;

import dev.magadiflo.patterns.plainjava.creational.builder.classic.product.Report;

public class ExcelReportBuilder implements ReportBuilder {

    private final Report report = new Report();

    @Override
    public void buildHeader(String title) {
        this.report.setHeader("[Excel sheet: " + title + "]");
    }

    @Override
    public void buildContent(String data) {
        this.report.setContent("Excel Rows: " + data);
    }

    @Override
    public void buildFooter(String author) {
        this.report.setFooter("Excel Footer - " + author);
    }

    @Override
    public Report getResult() {
        return this.report;
    }
}
