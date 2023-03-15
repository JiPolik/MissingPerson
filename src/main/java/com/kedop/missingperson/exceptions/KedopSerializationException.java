package com.kedop.missingperson.exceptions;

import com.kedop.missingperson.domain.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@SuppressWarnings(value = "unused")
public class KedopSerializationException extends KedopException {

    public static final HttpStatus ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public KedopSerializationException(String message, ErrorCode errorCode, Object... objects) {
        super(String.format(message, objects), errorCode, objects);
    }

    public KedopSerializationException(ErrorCode errorCode, Object... objects) {
        super(String.format(errorCode.getMessage(), objects), errorCode, objects);
    }

    @Override
    public String getKedopErrorCode() {
        return ExceptionCode.JSON_SERIALIZATION_ERROR.name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ERROR_STATUS;
    }
}
