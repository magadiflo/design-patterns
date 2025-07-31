package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.factory;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ParsedRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.processor.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class FileProcessorFactory {

    private final Map<FileType, FileProcessor<? extends ParsedRecord>> processors = new EnumMap<>(FileType.class);

    public FileProcessorFactory(List<FileProcessor<? extends ParsedRecord>> processorList) {
        processorList.forEach(processor -> {
            FileType fileType = processor.expectedFileType();
            this.processors.put(fileType, processor);
            log.info("Processor registrado: {} para tipo: {}", processor.getClass().getSimpleName(), fileType);
        });
        log.info("FileProcessorFactory inicializado con {} processors", processors.size());
    }

    public FileProcessor<? extends ParsedRecord> getProcessor(FileType fileType) {
        FileProcessor<? extends ParsedRecord> processor = this.processors.get(fileType);
        if (Objects.isNull(processor)) {
            throw new FileProcessorException("No hay processor disponible para: " + fileType);
        }
        log.debug("Obteniendo processor: {} para tipo: {}", processor.getClass().getSimpleName(), fileType);
        return processor;
    }

    public Set<FileType> getSupportedFileTypes() {
        return this.processors.keySet();
    }

}
