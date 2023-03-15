package com.kedop.missingperson.entities;

import com.kedop.missingperson.entities.Human.ColumnNames;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author oleksandrpolishchuk on 09.02.2023
 * @project MissingPerson
 */
@Getter
@Setter
@Entity
@Table(name = "human_photo")
public class HumanPhoto {
    @Id
    @Column(name = ColumnNames.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = ColumnNames.PHOTO_ID_BASE64_ENCODE)
    private String systemId;

    public static class ColumnNames {

        public static final String ID = "id";
        public static final String PHOTO_ID_BASE64_ENCODE = "encode_photo";
    }
}
