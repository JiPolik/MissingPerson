package com.kedop.missingperson.service;

import com.kedop.missingperson.model.HumanModel;
import java.util.List;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
public interface HumanService {

    List<HumanModel> getAllMissingHumans();

    HumanModel getMissingHumanById(String id);
}
