package com.kedop.missingperson.service.impl;

import com.kedop.missingperson.converter.HumanConverter;
import com.kedop.missingperson.domain.ErrorCode;
import com.kedop.missingperson.exceptions.CustomerException;
import com.kedop.missingperson.model.HumanModel;
import com.kedop.missingperson.repository.HumanRepository;
import com.kedop.missingperson.service.HumanService;
import jakarta.transaction.Transactional;
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
public class HumanServiceImpl implements HumanService {

    private final HumanRepository humanRepository;
    private final HumanConverter humanConverter;

    @Override
    @Transactional
    public List<HumanModel> getAllMissingHumans() {
        final var humans = humanRepository.findAll();
        log.info("There are: {} missing people", humans.size());
        return humanConverter.humansToHumanModels(humans);
    }

    @Override
    @Transactional
    public HumanModel getMissingHumanById(final String id) {
        final var human = humanRepository.findHumanById(id)
                .orElseThrow(() -> new CustomerException(ErrorCode.MISSING_PERSON_NOT_FOUND_BY_ID, id));
        log.info("Found missing human: [{}] with id: {}", human, id);
        return humanConverter.humanToHumanModel(human);
    }

}
