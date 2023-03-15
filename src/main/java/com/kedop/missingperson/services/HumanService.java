package com.kedop.missingperson.services;

import com.kedop.missingperson.domain.FilterCriteria;
import com.kedop.missingperson.models.HumanModel;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
public interface HumanService {

    List<HumanModel> getAllMissingHumans();
    HumanModel getMissingHumanById(Long id);
    void addMissingHuman(HumanModel humanModel);
    HumanModel updateMissingHuman(Long id, HumanModel humanModel);
    void removeMissingHuman(Long id);
    Page<HumanModel> findHuman(Pageable pageable, FilterCriteria filterCriteria);

}
