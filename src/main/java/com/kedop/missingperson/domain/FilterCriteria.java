package com.kedop.missingperson.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author oleksandrpolishchuk on 08.03.2023
 * @project MissingPerson
 */
@Value
@Builder
@Jacksonized
public class FilterCriteria {

    List<String> firstNameUkr;
    List<String> lastNameUkr;
    List<String> middleNameUkr;
    List<String> firstNameRus;
    List<String> lastNameRus;
    List<String> middleNameRus;
    List<String> firstNameEng;
    List<String> lastNameEng;
    List<String> middleNameEng;
    List<String> lostPlace;

}
