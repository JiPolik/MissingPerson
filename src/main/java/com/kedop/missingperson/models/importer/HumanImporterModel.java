package com.kedop.missingperson.model.importer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HumanImporterModel {

    private String id;
    @JsonProperty("ID")
    private String systemId;
    @JsonProperty("OVD")
    private String ovd;
    @JsonProperty("CATEGORY")
    private String category;
    @JsonProperty("FIRST_NAME_U")
    private String firstNameUkr;
    @JsonProperty("LAST_NAME_U")
    private String lastNameUkr;
    @JsonProperty("MIDDLE_NAME_U")
    private String middleNameUkr;
    @JsonProperty("FIRST_NAME_R")
    private String firstNameRus;
    @JsonProperty("LAST_NAME_R")
    private String lastNameRus;
    @JsonProperty("MIDDLE_NAME_R")
    private String middleNameRus;
    @JsonProperty("FIRST_NAME_E")
    private String firstNameEng;
    @JsonProperty("LAST_NAME_E")
    private String lastNameEng;
    @JsonProperty("MIDDLE_NAME_E")
    private String middleNameEng;
    @JsonProperty("BIRTH_DATE")
    private LocalDateTime birthDate;
    @JsonProperty("SEX")
    private String sex;
    @JsonProperty("LOST_DATE")
    private LocalDateTime lostDate;
    @JsonProperty("LOST_PLACE")
    private String lostPlace;
    @JsonProperty("ARTICLE_CRIM")
    private String articleCrim;
    @JsonProperty("RESTRAINT")
    private String restraint;
    @JsonProperty("CONTACT")
    private String contact;
    @JsonProperty("PHOTOID")
    private String photoId;

}
