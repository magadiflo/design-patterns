package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor;

import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.BusinessValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.CsvRecord;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

//Asumimos que todos los .csv que vamos a procesar tienen cabecera

@Slf4j
@RequiredArgsConstructor
public class CsvDelimitedProcessor extends FileProcessor<CsvRecord> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int EXPECTED_COLUMNS = 5;
    private static final String DELIMITER = ",";

    @Override
    protected ValidationResult validateFileStructure(Path path) {
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(path))) {
            String firstLine = reader.readLine();

            if (Objects.isNull(firstLine) || firstLine.isBlank()) {
                return ValidationResult.failure("Archivo vacío");
            }

            String[] columns = firstLine.split(Pattern.quote(DELIMITER));
            if (columns.length != EXPECTED_COLUMNS) {
                return ValidationResult.failure("El encabezado debe tener exactamente 5 columnas separadas por coma");
            }

            log.info("Encabezado CSV detectado correctamente: {} columnas", columns.length);
            return ValidationResult.success();
        } catch (IOException e) {
            return ValidationResult.failure("Error al leer el archivo: %s".formatted(e.getMessage()));
        }
    }

    @Override
    protected List<CsvRecord> parseFileContent(Path path) throws IOException {
        List<CsvRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(path))) {
            int lineNumber = 0;

            // Saltamos la cabecera
            String headers = reader.readLine();
            log.info("headers: {}", headers);

            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    log.warn("Línea {} vacía e ignorada", lineNumber);
                    continue;
                }

                try {
                    String[] fields = line.split(Pattern.quote(DELIMITER));
                    if (fields.length == EXPECTED_COLUMNS) {
                        CsvRecord csvRecord = new CsvRecord(
                                fields[0].trim(),
                                fields[1].trim(),
                                fields[2].trim(),
                                fields[3].trim(),
                                LocalDateTime.parse(fields[4].trim(), DATE_TIME_FORMATTER).toLocalDate()
                        );
                        records.add(csvRecord);
                    } else {
                        log.warn("Línea {} ignorada: se esperaban {} columnas pero se encontraron {}", lineNumber, EXPECTED_COLUMNS, fields.length);
                    }
                } catch (Exception e) {
                    log.warn("Error procesando línea {}: {}", lineNumber, e.getMessage());
                }
            }
        }

        log.info("Parsing CSV completado: {} registros extraídos", records.size());
        return records;
    }

    @Override
    protected BusinessValidationResult<CsvRecord> validateBusinessRules(List<CsvRecord> parsedRecords) {
        List<CsvRecord> validRecords = parsedRecords.stream()
                .filter(csvRecord -> {
                    boolean isValid = true;

                    if (Objects.isNull(csvRecord.firstName()) || csvRecord.firstName().isBlank()) {
                        log.warn("Registro inválido ID {}: firstName vacío", csvRecord.id());
                        isValid = false;
                    }

                    if (Objects.isNull(csvRecord.lastName()) || csvRecord.lastName().isBlank()) {
                        log.warn("Registro inválido ID {}: lastName vacío", csvRecord.id());
                        isValid = false;
                    }

                    if (Objects.isNull(csvRecord.email()) || !csvRecord.email().contains("@")) {
                        log.warn("Registro inválido ID {}: email inválido ({})", csvRecord.id(), csvRecord.email());
                        isValid = false;
                    }

                    if (Objects.nonNull(csvRecord.registrationDate()) && csvRecord.registrationDate().isAfter(LocalDate.now())) {
                        log.warn("Registro inválido ID {}: fecha de registro futura ({})", csvRecord.id(), csvRecord.registrationDate());
                        isValid = false;
                    }

                    return isValid;
                })
                .toList();
        int invalidCount = parsedRecords.size() - validRecords.size();
        return new BusinessValidationResult<>(validRecords, invalidCount);
    }

    @Override
    protected int storeRecords(List<CsvRecord> validRecords, String batchId) throws SQLException {
        validRecords.forEach(csvRecord -> {
            String sql = """
                        INSERT INTO users_csv (id, first_name, last_name, email, registration_date, batch_id)
                        VALUES ('%s', '%s', '%s', '%s', '%s', '%s')
                    """.formatted(
                    csvRecord.id(),
                    csvRecord.firstName(),
                    csvRecord.lastName(),
                    csvRecord.email(),
                    csvRecord.registrationDate(),
                    batchId
            );
            log.info("SQL simulada: {}", sql);
        });


        log.info("Se simularon {} inserciones en la tabla users_csv.", validRecords.size());
        return validRecords.size();
    }
}
