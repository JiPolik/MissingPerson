package com.peratera.common.provision.exceptions;

/**
 * &copy; 2021-2022 Peratera. All rights reserved.<br/><br/>
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Jun-21
 * @version 0.4.18
 * @since 1.0.0
 */
@SuppressWarnings(value = "unused")
public enum ExceptionCode {

    REPEATER_ERROR,
    JSON_SERIALIZATION_ERROR,
    JSON_DESERIALIZATION_ERROR,
    USER_ERROR,
    NOTIFICATION_ERROR,
    INSUFFICIENT_SCOPES,
    INSUFFICIENT_AUTHENTICATION,
    UNEXPECTED_OPERATION,
    CONSTRAINT_VIOLATION,
    JSON_PARSE_ERROR,
    RESOURCE_ACCESS_ERROR,
    REST_CLIENT_ERROR,
    MISSING_PARAMETER,
    CUSTOMER_ERROR,
    ADMIN_ERROR,
    UNEXPECTED_ERROR,
    DATA_ACCESS,
    PARAMETER_FORMAT_ERROR
}
