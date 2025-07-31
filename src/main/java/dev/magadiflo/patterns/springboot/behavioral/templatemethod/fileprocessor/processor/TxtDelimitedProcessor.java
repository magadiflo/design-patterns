package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.processor;


import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.BusinessValidationResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.TxtRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ValidationResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TxtDelimitedProcessor extends FileProcessor<TxtRecord> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String delimiter = "|";
    private final boolean hasHeader = false;
    private final int expectedColumns = 5;

    public TxtDelimitedProcessor(FileStorageService fileStorageService) {
        super(fileStorageService);
    }

    @Override
    public FileType expectedFileType() {
        return FileType.TXT;
    }

    @Override
    protected ValidationResult validateFileStructure(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String firstLine = reader.readLine();

            if (Objects.isNull(firstLine) || firstLine.isBlank()) {
                return ValidationResult.failure("Archivo txt vacío");
            }

            String[] columns = firstLine.split(Pattern.quote(this.delimiter));
            if (this.hasHeader) {
                if (columns.length < this.expectedColumns) {
                    return ValidationResult.failure("La cabecera txt debe tener al menos %d columnas separadas por '%s'"
                            .formatted(this.expectedColumns, this.delimiter));
                }
                log.info("Cabecera txt detectada. Columnas encontradas: {}", columns.length);
            } else {
                if (columns.length != this.expectedColumns) {
                    return ValidationResult.failure("La primera línea txt debe tener exactamente %d columnas separadas por '%s'"
                            .formatted(this.expectedColumns, this.delimiter));
                }
                log.info("Sin cabecera TXT. Validando estructura de la primera línea: {} columnas", columns.length);
            }

            return ValidationResult.success();
        } catch (IOException e) {
            return ValidationResult.failure("Error al leer el archivo: %s".formatted(e.getMessage()));
        }
    }

    @Override
    protected List<TxtRecord> parseFileContent(Path path) {
        List<TxtRecord> records = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            int lineNumber = 0;

            if (this.hasHeader) {
                String headers = reader.readLine();
                log.info("headers txt: {}", headers);
                lineNumber++;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    log.warn("Línea {} txt vacía e ignorada", lineNumber);
                    continue;
                }

                try {
                    String[] fields = line.split(Pattern.quote(this.delimiter));
                    if (fields.length == this.expectedColumns) {
                        TxtRecord txtRecord = new TxtRecord(
                                "TXT_" + lineNumber,
                                fields[0].trim(),
                                Double.parseDouble(fields[1].trim()),
                                LocalDateTime.parse(fields[2].trim(), DATE_TIME_FORMATTER),
                                fields[3].trim()
                        );
                        records.add(txtRecord);
                    } else {
                        log.warn("Línea {} txt ignorada: se esperaban {} columnas pero se encontraron {}", lineNumber, expectedColumns, fields.length);
                    }
                } catch (Exception e) {
                    log.warn("Error procesando línea {} txt: {}", lineNumber, e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error("Error leyendo archivo TXT: {}", e.getMessage());
            throw new FileProcessorException("Error procesando archivo TXT: " + e.getMessage());
        }

        log.info("Parsing TXT completado: {} registros extraídos", records.size());
        return records;
    }

    @Override
    protected BusinessValidationResult<TxtRecord> validateBusinessRules(List<TxtRecord> parsedRecords) {
        List<TxtRecord> validRecords = parsedRecords.stream()
                .filter(txtRecord -> {
                    boolean isValid = true;

                    if (txtRecord.accountNumber() == null || txtRecord.accountNumber().isBlank()) {
                        log.warn("Registro TXT inválido {}: accountNumber vacío", txtRecord.id());
                        isValid = false;
                    }

                    if (txtRecord.amount() <= 0) {
                        log.warn("Registro TXT inválido {}: amount debe ser mayor que 0", txtRecord.id());
                        isValid = false;
                    }

                    return isValid;
                })
                .toList();

        int invalidCount = parsedRecords.size() - validRecords.size();
        log.info("Validación TXT: {} registros válidos, {} inválidos", validRecords.size(), invalidCount);

        return new BusinessValidationResult<>(validRecords, invalidCount);
    }

    @Override
    protected int storeRecords(List<TxtRecord> validRecords, String batchId) {
        log.info("=== Simulación Persistencia TXT ===");
        validRecords.forEach(txtRecord -> {
            String sql = """
                        INSERT INTO transactions_txt (id, account_number, amount, transaction_date, description, batch_id)
                        VALUES ('%s', '%s', %.2f, '%s', '%s', '%s')
                    """.formatted(
                    txtRecord.id(),
                    txtRecord.accountNumber(),
                    txtRecord.amount(),
                    txtRecord.transactionDate(),
                    txtRecord.description(),
                    batchId
            );

            log.info("SQL simulada TXT: {}", sql);
        });
        log.info("Se simularon {} inserciones en la tabla transactions_txt.", validRecords.size());
        return validRecords.size();
    }
}
