package com.kedop.missingperson.models.importer;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class HumanPhotoImporterModel {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("PHOTOIDBASE64ENCODE")
    private String photoIdBase64;

}
