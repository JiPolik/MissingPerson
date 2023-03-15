package com.peratera.common.provision.exceptions;

import com.peratera.common.provision.domain.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * &copy; 2021 Peratera. All rights reserved.<br/><br/>
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Jun-21
 * @version 0.2.1
 * @since 1.0.0
 */
@Getter
@SuppressWarnings("unused")
public abstract class PerateraException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] objects;

    public PerateraException(String message, ErrorCode errorCode, Object... objects) {
        super(message);
        this.errorCode = errorCode;
        this.objects = objects;
    }

    public abstract String getPerateraErrorCode();
    public abstract HttpStatus getHttpStatus();
}
