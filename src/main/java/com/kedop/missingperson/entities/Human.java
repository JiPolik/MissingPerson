package com.kedop.missingperson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Getter
@Setter
@Entity
@Table(name = "human")
public class Human {
    @Id
    @Column(name = ColumnNames.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = ColumnNames.SYSTEM_ID)
    private String systemId;
    @Column(name = ColumnNames.OVD)
    private String ovd;
    @Column(name = ColumnNames.CATEGORY)
    private String category;
    @Column(name = ColumnNames.FIRST_NAME_UKR)
    private String firstNameUkr;
    @Column(name = ColumnNames.LAST_NAME_UKR)
    private String lastNameUkr;
    @Column(name = ColumnNames.MIDDLE_NAME_UKR)
    private String middleNameUkr;
    @Column(name = ColumnNames.FIRST_NAME_RUS)
    private String firstNameRus;
    @Column(name = ColumnNames.LAST_NAME_RUS)
    private String lastNameRus;
    @Column(name = ColumnNames.MIDDLE_NAME_RUS)
    private String middleNameRus;
    @Column(name = ColumnNames.FIRST_NAME_ENG)
    private String firstNameEng;
    @Column(name = ColumnNames.LAST_NAME_ENG)
    private String lastNameEng;
    @Column(name = ColumnNames.MIDDLE_NAME_ENG)
    private String middleNameEng;
    @Column(name = ColumnNames.BIRTH_DATE)
    private LocalDateTime birthDate;
    @Column(name = ColumnNames.SEX)
    private String sex;
    @Column(name = ColumnNames.LOST_DATE)
    private LocalDateTime lostDate;
    @Column(name = ColumnNames.LOST_PLACE)
    private String lostPlace;
    @Column(name = ColumnNames.ARTICLE_CRIM)
    private String articleCrim;
    @Column(name = ColumnNames.RESTRAINT)
    private String restraint;
    @Column(name = ColumnNames.CONTACT)
    private String contact;
    @Column(name = ColumnNames.PHOTO_ID)
    private String photoId;


    public static class ColumnNames {

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
