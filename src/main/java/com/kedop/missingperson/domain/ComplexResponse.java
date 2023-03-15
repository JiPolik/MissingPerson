package com.kedop.missingperson.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kedop.missingperson.domain.ComplexResponse.JsonKeys;
import com.kedop.missingperson.serializers.ComplexResponseSerializer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Getter
@SuppressWarnings(value = "unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize(using = ComplexResponseSerializer.class)
@JsonPropertyOrder(value = { JsonKeys.STATUS_VALUE, JsonKeys.SUCCESS_VALUE, JsonKeys.ERRORS_VALUE,
        JsonKeys.PAYLOAD_VALUE})
public class ComplexResponse<T> {

    private final int status;
    private final boolean success;
    private List<ComplexError> errors;
    private T payload;

    @JsonIgnore
    public ComplexResponse(HttpStatus httpStatus) {
        status = httpStatus.value();
        success = !httpStatus.isError();
    }

    @JsonIgnore
    public ComplexResponse(int status, boolean success) {
        this.status = status;
        this.success = success;
    }

    @JsonIgnore
    public ComplexResponse(HttpStatus httpStatus, boolean success, T payload) {
        this(httpStatus.value(), success);
        if (!success) {
            throw new IllegalArgumentException("HTTP Status must have value from success groups");
        }
        this.payload = payload;
    }

    @JsonIgnore
    public ComplexResponse(HttpStatus httpStatus, String errorCode, String errorDescription, ErrorCode code,
            Object... objects) {
        this(httpStatus);
        if (success) {
            throw new IllegalArgumentException("HTTP Status must have value from error groups");
        }
        errors = new ArrayList<>();
        var complexError = new ComplexError(errorCode, errorDescription, code.getCode(), objects);
        errors.add(complexError);
    }

    @JsonIgnore
    public ComplexResponse(HttpStatus httpStatus, String errorCode, ErrorCode code, Object... objects) {
        this(httpStatus);
        if (success) {
            throw new IllegalArgumentException("HTTP Status must have value from error groups");
        }
        errors = new ArrayList<>();
        var complexError = new ComplexError(errorCode, code, objects);
        errors.add(complexError);
    }

    @JsonIgnore
    public ComplexResponse(HttpStatus httpStatus, ComplexError... errors) {
        this(httpStatus);
        if (success) {
            throw new IllegalArgumentException("HTTP Status must have value from error groups");
        }
        this.errors = Arrays.asList(errors);
    }

    @JsonCreator
    public ComplexResponse(@JsonProperty(value = JsonKeys.STATUS_VALUE, required = true) Integer status,
            @JsonProperty(value = JsonKeys.SUCCESS_VALUE, required = true) Boolean success,
            @JsonProperty(value = JsonKeys.ERRORS_VALUE) List<ComplexError> errors,
            @JsonProperty(value = JsonKeys.PAYLOAD_VALUE) T payload) {
        this.status = status;
        this.success = success;
        this.errors = errors;
        this.payload = payload;
    }

    public void addError(final ComplexError error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    @Override
    public String toString() {
        var complexErrorsToString = errors == null ? "null"
                : "[" + errors.stream().map(ComplexError::toString).collect(Collectors.joining(", ")) + "]";
        return String.format("%s {%s=%d, %s=%s, %s=%d, %s=%s, %s=%s, %s='%s', %s='%s'}", getClass().getSimpleName(),
                JsonKeys.STATUS_VALUE, status, JsonKeys.SUCCESS_VALUE, success,
                JsonKeys.ERRORS_VALUE, complexErrorsToString, JsonKeys.PAYLOAD_VALUE, payload);
    }

    public static class JsonKeys {

        public static final String STATUS_VALUE = "status";
        public static final String SUCCESS_VALUE = "success";
        public static final String ERRORS_VALUE = "errors";
        public static final String PAYLOAD_VALUE = "payload";
    }
}
