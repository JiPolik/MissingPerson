package com.peratera.common.provision.exceptions;

import com.peratera.common.provision.domain.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * &copy; 2021-2022 Peratera. All rights reserved.<br/><br/>
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Aug-06
 * @version 0.4.25
 * @since 1.0.0
 */
@SuppressWarnings(value = "unused")
public class CustomerException extends PerateraException {

    private static final long serialVersionUID = 8767217354002806522L;

    public static final HttpStatus ERROR_STATUS = HttpStatus.BAD_REQUEST;

    public CustomerException(String message, ErrorCode errorCode, Object... objects) {
        super(String.format(message, objects), errorCode, objects);
    }

    public CustomerException(ErrorCode errorCode, Object... objects) {
        super(String.format(errorCode.getMessage(), objects), errorCode, objects);
    }

    public String getPerateraErrorCode() {
        return ExceptionCode.CUSTOMER_ERROR.name();
    }

    public HttpStatus getHttpStatus() {
        return ERROR_STATUS;
    }
}
