package com.kedop.missingperson.service.impl;

import com.kedop.missingperson.service.DataImporterService;
import com.kedop.missingperson.utils.DataImporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataImporterServiceImpl implements DataImporterService {

    private final DataImporter dataImporter;

    @Override
    public void importData() {
        log.info("Start importing data...");
        dataImporter.importIncomingData();
        log.info("Finished import data!");
    }
}
