package com.kedop.missingperson.controllers;

import com.kedop.missingperson.config.ApplicationConfiguration;
import com.kedop.missingperson.domain.ComplexResponse;
import com.kedop.missingperson.models.HumanModel;
import com.kedop.missingperson.services.HumanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ComplexResponse<HumanModel> getMissingHuman(@PathVariable final Long id) {
        return new ComplexResponse<>(HttpStatus.OK, true, humanService.getMissingHumanById(id));
    }

    @PostMapping(path = ApplicationConfiguration.HUMANS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ComplexResponse<Void> addMissingHuman(@RequestBody final HumanModel humanModel) {
        humanService.addMissingHuman(humanModel);
        return new ComplexResponse<>(HttpStatus.CREATED);
    }

    @PutMapping(path = ApplicationConfiguration.HUMAN_BY_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ComplexResponse<HumanModel> updateMissingHuman(@PathVariable final Long id,
            @RequestBody final HumanModel humanModel) {
        return new ComplexResponse<>(HttpStatus.OK, true, humanService.updateMissingHuman(id, humanModel));
    }

    @DeleteMapping(path = ApplicationConfiguration.HUMAN_BY_ID_PATH)
    public ComplexResponse<Void> deleteMissingHuman(@PathVariable final Long id) {
        humanService.removeMissingHuman(id);
        return new ComplexResponse<>(HttpStatus.OK);
    }

}
