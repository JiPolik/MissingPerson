package com.kedop.missingperson.config;

import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */

@Getter
@Configuration
@EnableConfigurationProperties
public class ApplicationConfiguration {

    public static final String IMPORT_DATA_PATH = "/import";
    public static final String HUMANS_PATH = "/humans";
    public static final String HUMAN_BY_ID_PATH = HUMANS_PATH + "/{id}";
}
