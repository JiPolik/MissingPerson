package com.kedop.missingperson.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

/**
 * @author oleksandrpolishchuk on 08.03.2023
 * @project MissingPerson
 */
@Value
@Builder
@Validated
@Jacksonized
public class Range<T> {

    @NotNull T from;
    @NotNull T to;

}
