package com.kedop.missingperson.services.impl;

import com.kedop.missingperson.converters.HumanConverter;
import com.kedop.missingperson.domain.ErrorCode;
import com.kedop.missingperson.domain.FilterCriteria;
import com.kedop.missingperson.entities.Human;
import com.kedop.missingperson.exceptions.CustomerException;
import com.kedop.missingperson.models.HumanModel;
import com.kedop.missingperson.repositories.HumanRepository;
import com.kedop.missingperson.repositories.Specifications;
import com.kedop.missingperson.services.HumanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public HumanModel getMissingHumanById(final Long id) {
        final var human = humanRepository.findHumanById(id)
                .orElseThrow(() -> new CustomerException(ErrorCode.MISSING_PERSON_GET_NOT_FOUND_BY_ID, id));
        log.info("Found missing human: [{}] with id: {}", human, id);
        return humanConverter.humanToHumanModel(human);
    }

    @Override
    @Transactional
    public void addMissingHuman(HumanModel humanModel) {
        humanRepository.save(humanConverter.humanModelToHuman(humanModel));
        log.info("Added a new missed human: {}", humanModel);
    }

    @Override
    public HumanModel updateMissingHuman(Long id, HumanModel humanModel) {
        log.info("Updating missing human with id: {}", id);
        final var missingHuman = humanRepository.findHumanById(id)
                .orElseThrow(() -> new CustomerException(ErrorCode.MISSING_PERSON_UPDATE_NOT_FOUND_BY_ID, id));
        humanConverter.updateFromHumanModelToHuman(humanModel, missingHuman);
        final var updatedHumanModel = humanConverter.humanToHumanModel(humanRepository.save(missingHuman));
        log.info("Updated human: {}", updatedHumanModel);
        return updatedHumanModel;
    }

    @Override
    public void removeMissingHuman(Long id) {
        log.info("Removing missing human by id: {}", id);
        humanRepository.deleteById(id);
    }

    @Override
    public Page<HumanModel> findHuman(Pageable pageable, FilterCriteria filterCriteria) {
        final Specification<Human> spec = Specifications.criteriaToSpec(filterCriteria);
        return new PageImpl<>(humanConverter.humansToHumanModels(humanRepository.findAll(spec, pageable).getContent()));
    }
}
