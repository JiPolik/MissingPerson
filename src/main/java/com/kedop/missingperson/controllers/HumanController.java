package com.kedop.missingperson.controller;

import com.kedop.missingperson.config.ApplicationConfiguration;
import com.kedop.missingperson.domain.ComplexResponse;
import com.kedop.missingperson.models.HumanModel;
import com.kedop.missingperson.services.HumanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class HumanController {

    private final HumanService humanService;

    @GetMapping(ApplicationConfiguration.HUMANS_PATH)
    public ComplexResponse<List<HumanModel>> getAllMissingHumans() {
        return new ComplexResponse<>(HttpStatus.OK, true, humanService.getAllMissingHumans());
    }

    @GetMapping(ApplicationConfiguration.HUMAN_BY_ID_PATH)
    public ComplexResponse<HumanModel> getMissingHuman(@PathVariable final String id) {
        return new ComplexResponse<>(HttpStatus.OK, true, humanService.getMissingHumanById(id));
    }


}
