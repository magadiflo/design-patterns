package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor;

import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.BusinessValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.TxtRecord;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class TxtDelimitedProcessor extends FileProcessor<TxtRecord> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String delimiter;
    private final boolean hasHeader;
    private final int expectedColumns;

    @Override
    protected ValidationResult validateFileStructure(Path path) {
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(path))) {
            String firstLine = reader.readLine();

            if (Objects.isNull(firstLine) || firstLine.isBlank()) {
                return ValidationResult.failure("Archivo vacío");
            }

            // Si el archivo tiene encabezado, validamos que al menos tenga expectedColumns,
            // pero no validamos el contenido de datos aún.
            String[] columns = firstLine.split(Pattern.quote(this.delimiter));
            if (this.hasHeader) {
                if (columns.length < this.expectedColumns) {
                    return ValidationResult.failure("La cabecera debe tener al menos %d columnas separadas por '%s'"
                            .formatted(this.expectedColumns, this.delimiter));
                }
                log.info("Cabecera detectada. Columnas encontradas: {}", columns.length);
            } else {
                // Si no hay cabecera, consideramos que la primera línea ya es un registro
                if (columns.length != this.expectedColumns) {
                    return ValidationResult.failure("La primera línea debe tener exactamente %d columnas separadas por '%s'"
                            .formatted(this.expectedColumns, this.delimiter));
                }
                log.info("No se detectó cabecera. Validando estructura de la primera línea de datos: {} columnas", columns.length);
            }

            return ValidationResult.success();
        } catch (IOException e) {
            return ValidationResult.failure("Error al leer el archivo: %s".formatted(e.getMessage()));
        }
    }

    @Override
    protected List<TxtRecord> parseFileContent(Path path) throws IOException {
        List<TxtRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(path))) {
            int lineNumber = 0;

            if (this.hasHeader) {
                reader.readLine(); // Se puede guardar el header si es necesario
                lineNumber++;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    log.warn("Línea {} vacía e ignorada", lineNumber);
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
                        log.warn("Línea {} ignorada: se esperaban {} columnas pero se encontraron {}", lineNumber, expectedColumns, fields.length);
                    }
                } catch (Exception e) {
                    log.warn("Error procesando línea {}: {}", lineNumber, e.getMessage());
                }
            }
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
                        log.warn("Registro inválido {}: accountNumber vacío", txtRecord.id());
                        isValid = false;
                    }

                    if (txtRecord.amount() <= 0) {
                        log.warn("Registro inválido {}: amount debe ser mayor que 0", txtRecord.id());
                        isValid = false;
                    }

                    return isValid;
                })
                .toList();
        int invalidCount = parsedRecords.size() - validRecords.size();
        return new BusinessValidationResult<>(validRecords, invalidCount);
    }

    @Override
    protected int storeRecords(List<TxtRecord> validRecords, String batchId) throws SQLException {
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

            log.info("SQL simulada: {}", sql);
        });


        log.info("Se simularon {} inserciones en la tabla transactions_txt.", validRecords.size());
        return validRecords.size();
    }
}
