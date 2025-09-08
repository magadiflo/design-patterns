package dev.magadiflo.patterns.plainjava.creational.builder.classic.client;

import dev.magadiflo.patterns.plainjava.creational.builder.classic.builder.ExcelReportBuilder;
import dev.magadiflo.patterns.plainjava.creational.builder.classic.builder.PDFReportBuilder;
import dev.magadiflo.patterns.plainjava.creational.builder.classic.builder.ReportBuilder;
import dev.magadiflo.patterns.plainjava.creational.builder.classic.director.ReportDirector;
import dev.magadiflo.patterns.plainjava.creational.builder.classic.product.Report;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportGenerationsService {
    public static void main(String[] args) {
        ReportBuilder pdfBuilder = new PDFReportBuilder();
        ReportDirector director = new ReportDirector(pdfBuilder);
        Report pdfReport = director.build("Reporte de ventas", "Datos de venta aquí", "Milagros Díaz");
        log.info(pdfReport.toString());

        ReportBuilder excelBuilder = new ExcelReportBuilder();
        director.changeBuilder(excelBuilder);
        Report excelReport = director.build("Reporte de ventas", "Datos de venta aquí", "Milagros Díaz");
        log.info(excelReport.toString());
    }
}
