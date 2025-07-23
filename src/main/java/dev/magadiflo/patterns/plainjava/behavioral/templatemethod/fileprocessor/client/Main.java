package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.client;

import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.CsvRecord;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ProcessResult;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.TxtRecord;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor.CsvDelimitedProcessor;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor.FileProcessor;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor.TxtDelimitedProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        FileProcessor<TxtRecord> txtProcessor = new TxtDelimitedProcessor("|", false, 4);
        String txtFilePath = "src/main/resources/sample-files/sample_transactions.txt";
        String txtBatchId = "BATCH_" + System.currentTimeMillis();
        ProcessResult txtResult = txtProcessor.processFile(txtFilePath, txtBatchId);
        printResult(txtResult);

        log.info("\n\n");

        FileProcessor<CsvRecord> csvProcessor = new CsvDelimitedProcessor();
        String csvFilePath = "src/main/resources/sample-files/sample_users.csv";
        String csvBatchId = "BATCH_" + System.currentTimeMillis();
        ProcessResult csvResult = csvProcessor.processFile(csvFilePath, csvBatchId);
        printResult(csvResult);
    }

    private static void printResult(ProcessResult result) {
        log.info(result.toString());
    }
}
