package com.peratera.common.provision.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.peratera.common.provision.domain.ComplexError.JsonKeys;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * &copy; 2021 Peratera. All rights reserved.<br/><br/>
 *
 * Sub-object for {@link ComplexResponse} object with {@link #code error code} and
 * {@link #description error description}. If error is present in the {@link ComplexResponse} both fields -
 * {@link #code} and {@link #description} must be present.
 * Note: {@link #code error code} always should be uppercase.
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Jun-21
 * @version 0.2.5
 * @since 1.0.0
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = { JsonKeys.ERROR_CODE, JsonKeys.ERROR_DESCRIPTION, JsonKeys.ERROR_ID })
public class ComplexError {

    private final String code;
    private final String description;
    private final Integer id;
    private final Object[] objects;

    @JsonCreator
    public ComplexError(@JsonProperty(value = JsonKeys.ERROR_CODE, required = true) String code,
            @JsonProperty(value = JsonKeys.ERROR_DESCRIPTION, required = true) String description,
            @JsonProperty(value = JsonKeys.ERROR_ID, required = true) Integer id,
            @JsonProperty(value = JsonKeys.ERROR_OBJECTS) Object... objects) {
        this.code = code;
        this.description = String.format(description, objects);
        this.id = id;
        this.objects = objects;
    }

    @JsonIgnore
    public ComplexError(String code, ErrorCode errorCode, Object... objects) {
        this.code = code;
        this.description = String.format(errorCode.getMessage(), objects);
        this.id = errorCode.getCode();
        this.objects = objects;
    }

    @Override
    public String toString() {
        return String.format("%s {%s='%s', %s='%s', %s='%s', %s=[%s]}", getClass().getSimpleName(), JsonKeys.ERROR_ID,
                id, JsonKeys.ERROR_CODE, code, JsonKeys.ERROR_DESCRIPTION, description, JsonKeys.ERROR_OBJECTS,
                objects == null ? "" : Arrays.stream(objects).map(Object::toString).collect(Collectors.joining(", ")));
    }

    public static class JsonKeys {

        public static final String ERROR_CODE = "code";
        public static final String ERROR_DESCRIPTION = "description";
        public static final String ERROR_ID = "id";
        public static final String ERROR_OBJECTS = "objects";
    }
}
