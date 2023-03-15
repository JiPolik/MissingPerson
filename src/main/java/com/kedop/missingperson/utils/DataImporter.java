package com.kedop.missingperson.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kedop.missingperson.converters.HumanConverter;
import com.kedop.missingperson.models.importer.HumanImporterModel;
import com.kedop.missingperson.repositories.HumanRepository;
import java.io.IOException;
import java.util.List;
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
public class DataImporter {
    private final String HUMAN_JSON_FILE_PATH = "jsonData/mvswantedbezvesti-1.json";
    private final String HUMAN_PHOTO_JSON_FILE_PATH = "jsonData/mvswantedbezvesti-photof.json";
    private final HumanRepository humanRepository;
    private final ObjectMapper objectMapper;
    private final HumanConverter humanConverter;

    public void importIncomingHumanData() {
        try {
            final var humansImporterModels = objectMapper.readValue(getClass().getClassLoader()
                            .getResourceAsStream(HUMAN_JSON_FILE_PATH),
                    new TypeReference<List<HumanImporterModel>>() {});
            final var humans = humanConverter.humanImporterModelsToHumans(humansImporterModels);
            humanRepository.saveAll(humans);
        } catch (IOException e) {
            log.error("Can't parse input file with data of missed humans");
            throw new RuntimeException(e);
        }
    }
}
