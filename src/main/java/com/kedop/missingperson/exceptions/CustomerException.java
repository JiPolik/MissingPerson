package com.kedop.missingperson.exceptions;

import com.kedop.missingperson.domain.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@SuppressWarnings(value = "unused")
@Getter
public class CustomerException extends KedopException {

    private static final long serialVersionUID = 8767217354002806522L;

    public static final HttpStatus ERROR_STATUS = HttpStatus.BAD_REQUEST;

    public CustomerException(String message, ErrorCode errorCode, Object... objects) {
        super(String.format(message, objects), errorCode, objects);
    }

    public CustomerException(ErrorCode errorCode, Object... objects) {
        super(String.format(errorCode.getMessage(), objects), errorCode, objects);
    }

    public String getKedopErrorCode() {
        return ExceptionCode.CUSTOMER_ERROR.name();
    }

    public HttpStatus getHttpStatus() {
        return ERROR_STATUS;
    }
}
