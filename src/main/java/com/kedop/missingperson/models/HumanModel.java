package com.kedop.missingperson.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class HumanModel {

    @JsonProperty(value = JsonKeys.ID)
    private String id;
    @JsonProperty(value = JsonKeys.SYSTEM_ID)
    private String systemId;
    @JsonProperty(value = JsonKeys.OVD)
    private String ovd;
    @JsonProperty(value = JsonKeys.CATEGORY)
    private String category;
    @JsonProperty(value = JsonKeys.FIRST_NAME_UKR)
    private String firstNameUkr;
    @JsonProperty(value = JsonKeys.LAST_NAME_UKR)
    private String lastNameUkr;
    @JsonProperty(value = JsonKeys.MIDDLE_NAME_UKR)
    private String middleNameUkr;
    @JsonProperty(value = JsonKeys.FIRST_NAME_RUS)
    private String firstNameRus;
    @JsonProperty(value = JsonKeys.LAST_NAME_RUS)
    private String lastNameRus;
    @JsonProperty(value = JsonKeys.MIDDLE_NAME_RUS)
    private String middleNameRus;
    @JsonProperty(value = JsonKeys.FIRST_NAME_ENG)
    private String firstNameEng;
    @JsonProperty(value = JsonKeys.LAST_NAME_ENG)
    private String lastNameEng;
    @JsonProperty(value = JsonKeys.MIDDLE_NAME_ENG)
    private String middleNameEng;
    @JsonProperty(value = JsonKeys.BIRTH_DATE)
    private LocalDateTime birthDate;
    @JsonProperty(value = JsonKeys.SEX)
    private String sex;
    @JsonProperty(value = JsonKeys.LOST_DATE)
    private LocalDateTime lostDate;
    @JsonProperty(value = JsonKeys.LOST_PLACE)
    private String lostPlace;
    @JsonProperty(value = JsonKeys.ARTICLE_CRIM)
    private String articleCrim;
    @JsonProperty(value = JsonKeys.RESTRAINT)
    private String restraint;
    @JsonProperty(value = JsonKeys.CONTACT)
    private String contact;
    @JsonProperty(value = JsonKeys.PHOTO_ID)
    private String photoId;

    public static final class JsonKeys {

        public static final String ID = "id";
        public static final String SYSTEM_ID = "system_id";
        public static final String OVD = "ovd";
        public static final String CATEGORY = "category";
        public static final String FIRST_NAME_UKR = "first_name_u";
        public static final String LAST_NAME_UKR = "last_name_u";
        public static final String MIDDLE_NAME_UKR = "middle_name_u";
        public static final String FIRST_NAME_RUS = "first_name_r";
        public static final String LAST_NAME_RUS = "last_name_r";
        public static final String MIDDLE_NAME_RUS = "middle_name_r";
        public static final String FIRST_NAME_ENG = "first_name_e";
        public static final String LAST_NAME_ENG = "last_name_e";
        public static final String MIDDLE_NAME_ENG = "middle_name_e";
        public static final String BIRTH_DATE = "birth_date";
        public static final String SEX = "sex";
        public static final String LOST_DATE = "lost_date";
        public static final String LOST_PLACE = "lost_place";
        public static final String ARTICLE_CRIM = "article_crim";
        public static final String RESTRAINT = "restraint";
        public static final String CONTACT = "contact";
        public static final String PHOTO_ID = "photo_id";
    }
}
