package com.kedop.missingperson.exceptions;

import com.kedop.missingperson.domain.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Getter
@SuppressWarnings("unused")
public abstract class KedopException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] objects;

    public KedopException(String message, ErrorCode errorCode, Object... objects) {
        super(message);
        this.errorCode = errorCode;
        this.objects = objects;
    }

    public abstract String getKedopErrorCode();
    public abstract HttpStatus getHttpStatus();
}
