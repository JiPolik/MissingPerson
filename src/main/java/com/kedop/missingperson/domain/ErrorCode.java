package com.kedop.missingperson.domain;

import lombok.Getter;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Getter
public enum ErrorCode {

    //missing-person
    MISSING_PERSON_GET_NOT_FOUND_BY_ID(1001, "Missing person with ID [%s] not found"),
    MISSING_PERSON_UPDATE_NOT_FOUND_BY_ID(1002, "Missing person with ID [%s] not found"),

    //support
    INCORRECT_HTTP_STATUS(9001, "HTTP Status does not match to response success"),
    UNEXPECTED_ERROR_ON_SUCCESS(9002, "Unexpected errors field with success response"),
    STRING_DESERIALIZATION_FAILED(9003, "Deserialization of String value is failed"),
    SERIALIZATION_VARIABLES_ERROR(9004, "Cannot serialize variables [%s]"),
    UNKNOWN_ERROR_CODE(9005, "Unknown error code for field [%s]"),
    CONVERSION_ERROR(23001, "Failed to convert value [%s] to required type [%s]"),
    UNHANDLED_ERROR(21008, "Unhandled error: [%s]"),
    DATA_ACCESS_ERROR(21009, "Could not execute statement; class [%s]; error: [%s]"),
    UNEXPECTED_ERROR(21010, "Unexpected error, please contact Administrators: [%s]"),
    WRONG_RESPONSE_OBJECT(22001, "Invalid endpoint return type: [%s]"),
    MISSING_REQUEST_PARAMETER(21006, "Required [%s] parameter [%s] is not present"),
    REQUEST_METHOD_NOT_SUPPORTED(21007, "Request method [%s] not supported"),
    JSON_PARSING_ERROR(21001, "JSON parsing error: [%s]"),
    HTTP_MESSAGE_NOT_READABLE(21003, "HTTP message parsing error: [%s]"),
    RESOURCE_ACCESS_ERROR(21004, "Inner REST interaction error: [%s]");




    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
