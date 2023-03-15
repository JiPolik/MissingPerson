package com.kedop.missingperson.controller;

import com.kedop.missingperson.config.ApplicationConfiguration;
import com.kedop.missingperson.domain.ComplexResponse;
import com.kedop.missingperson.services.DataImporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class DataImporterController {

    private final DataImporterService dataImporterService;

    @GetMapping(ApplicationConfiguration.IMPORT_DATA_PATH)
    public ComplexResponse<Void> importData() {
        dataImporterService.importData();
        return new ComplexResponse<>(HttpStatus.OK);
    }

}
