package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.impl;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.TxtRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileProcessor;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileStorageService;
import org.springframework.stereotype.Service;

@Service
public class TxtDelimitedProcessor extends FileProcessor<TxtRecord> {

    public TxtDelimitedProcessor(FileStorageService storeFileService) {
        super(storeFileService);
    }

    @Override
    protected String expectedExtension() {
        return FileType.TXT.getExtension();
    }
}
