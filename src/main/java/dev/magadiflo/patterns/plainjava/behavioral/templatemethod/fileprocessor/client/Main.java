package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.client;

import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ProcessResult;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.TxtRecord;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor.FileProcessor;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor.TxtDelimitedProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        FileProcessor<TxtRecord> txtProcessor = new TxtDelimitedProcessor("|", false, 4);
        String filePath = "src/main/resources/sample-files/sample_transactions.txt";
        String batchId = "BATCH_" + System.currentTimeMillis();
        ProcessResult result = txtProcessor.processFile(filePath, batchId);
        printResult(result);
    }

    private static void printResult(ProcessResult result) {
        log.info(result.toString());
    }
}
