package com.kedop.missingperson.services.impl;

import com.kedop.missingperson.services.DataImporterService;
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
    public void importHumanData() {
        log.info("Start importing human data...");
        dataImporter.importIncomingHumanData();
        log.info("Finished import human data!");
    }

    @Override
    public void importHumanPhotoData() {

    }
}
