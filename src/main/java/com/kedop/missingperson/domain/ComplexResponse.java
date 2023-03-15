package com.peratera.common.provision.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.peratera.common.provision.domain.ComplexResponse.JsonKeys;
import com.peratera.common.provision.serializers.ComplexResponseSerializer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * &copy; 2021-2022 Peratera. All rights reserved.<br/><br/>
 *
 * The universal mapping object for any responses. May include payload or {@link #errors}, the presence of both methods
 * together is prohibited. Initials values of fields {@link #duration}, {@link #path} and {@link #method} set empty,
 * they should be set in finish filter(s).
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Jun-21
 * @version 0.4.14
 * @since 1.0.0
 */
@Getter
@SuppressWarnings(value = "unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonSerialize(using = ComplexResponseSerializer.class)
@JsonPropertyOrder(value = { JsonKeys.STATUS_VALUE, JsonKeys.SUCCESS_VALUE, JsonKeys.ERRORS_VALUE, JsonKeys.PAYLOAD_VALUE, JsonKeys.DURATION_VALUE,
        JsonKeys.PATH_VALUE, JsonKeys.METHOD_VALUE})
public class ComplexResponse<T> {

    private final int status;
    private final boolean success;
    private List<ComplexError> errors;
    private T payload;

    @Setter
    private long duration = 0;

    @Setter
    private String path = "";

    @Setter
    private String method = "";

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
            @JsonProperty(value = JsonKeys.DURATION_VALUE, required = true) Long duration,
            @JsonProperty(value = JsonKeys.PATH_VALUE, required = true) String path,
            @JsonProperty(value = JsonKeys.METHOD_VALUE, required = true) String method,
            @JsonProperty(value = JsonKeys.ERRORS_VALUE) List<ComplexError> errors,
            @JsonProperty(value = JsonKeys.PAYLOAD_VALUE) T payload) {
        this.status = status;
        this.success = success;
        this.duration = duration;
        this.path = path;
        this.method = method;
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
                JsonKeys.STATUS_VALUE, status, JsonKeys.SUCCESS_VALUE, success, JsonKeys.DURATION_VALUE, duration,
                JsonKeys.ERRORS_VALUE, complexErrorsToString, JsonKeys.PAYLOAD_VALUE, payload, JsonKeys.PATH_VALUE,
                path, JsonKeys.METHOD_VALUE, method);
    }

    public static class JsonKeys {

        public static final String STATUS_VALUE = "status";
        public static final String SUCCESS_VALUE = "success";
        public static final String DURATION_VALUE = "duration";
        public static final String ERRORS_VALUE = "errors";
        public static final String PAYLOAD_VALUE = "payload";
        public static final String PATH_VALUE = "path";
        public static final String METHOD_VALUE = "method";
    }
}
