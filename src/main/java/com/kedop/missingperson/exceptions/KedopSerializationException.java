package com.peratera.common.provision.exceptions;

import com.peratera.common.provision.domain.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * &copy; 2021-2022 Peratera. All rights reserved.<br/><br/>
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Jun-21
 * @version 0.4.18
 * @since 1.0.0
 */
@SuppressWarnings(value = "unused")
public class PerateraSerializationException extends PerateraException {

    public static final HttpStatus ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public PerateraSerializationException(String message, ErrorCode errorCode, Object... objects) {
        super(String.format(message, objects), errorCode, objects);
    }

    public PerateraSerializationException(ErrorCode errorCode, Object... objects) {
        super(String.format(errorCode.getMessage(), objects), errorCode, objects);
    }

    @Override
    public String getPerateraErrorCode() {
        return ExceptionCode.JSON_SERIALIZATION_ERROR.name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ERROR_STATUS;
    }
}
