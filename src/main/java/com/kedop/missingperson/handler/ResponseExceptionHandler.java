package com.kedop.missingperson.handler;


import static com.kedop.missingperson.utils.KedopConstants.EXCEPTION_LABEL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.kedop.missingperson.domain.ComplexError;
import com.kedop.missingperson.domain.ComplexResponse;
import com.kedop.missingperson.domain.ErrorCode;
import com.kedop.missingperson.exceptions.CustomerException;
import com.kedop.missingperson.exceptions.ExceptionCode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Slf4j
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = CustomerException.class)
    public ComplexResponse<Void> handle(final CustomerException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(exception.getHttpStatus(), new ComplexError(exception.getKedopErrorCode(),
                exception.getMessage(), exception.getErrorCode().getCode(), exception.getObjects()));
    }


    /**
     * According to
     * {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter #getArgumentsForConstraint} a first
     * argument indicating the field name. Afterwards, it adds all actual constraint annotation attributes (i.e.
     * excluding "message", "groups" and "payload") in alphabetical order of their attribute names.
     * <p>
     * Note! Please inform if API returns error with ID "30000"!
     *
     * @param exception {@link MethodArgumentNotValidException}
     * @return {@link ComplexResponse<Void>}
     * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter#getArgumentsForConstraint
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ComplexResponse<Void> handle(final MethodArgumentNotValidException exception) {
        final var complexResponse = new ComplexResponse<Void>(HttpStatus.BAD_REQUEST);
        processFieldErrors(exception.getBindingResult().getFieldErrors(), complexResponse);
        return complexResponse;
    }

    private static void processFieldErrors(final List<FieldError> fieldErrors,
            final ComplexResponse<Void> complexResponse) {
        fieldErrors.stream().filter(Objects::nonNull).forEach(error -> {
            var errorCode = ErrorCode.UNKNOWN_ERROR_CODE;
            final var arguments = error.getArguments();
            var objects = new Object[]{"unknown"};
            if (arguments != null && arguments.length > 0) {
                final var argumentList = Arrays.stream(arguments)
                        .filter(arg -> BeanUtils.isSimpleValueType(arg.getClass()) && !arg.getClass().isEnum())
                        .collect(Collectors.toList());
                final var fieldName = error.getField();
                try {
                    final var violation = error.unwrap(ConstraintViolation.class);
                    final var field = violation.getRootBeanClass().getDeclaredField(fieldName);
                    argumentList.add(0, field.getDeclaredAnnotation(JsonProperty.class).value());
                } catch (Exception throwable) {
                    argumentList.add(0, fieldName);
                }
                if (arguments.length > 1) {
                    errorCode = (ErrorCode) arguments[1];
                }
                argumentList.add(error.getRejectedValue());
                objects = argumentList.toArray();
            }
            complexResponse.addError(new ComplexError(ExceptionCode.CONSTRAINT_VIOLATION.name(), errorCode, objects));
        });
    }

    /**
     * Note! Please inform if API returns error with ID "30000"!
     *
     * @param exception {@link ConstraintViolationException}
     * @return {@link ComplexResponse<Void>}
     * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter#getArgumentsForConstraint
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ComplexResponse<Void> handle(final ConstraintViolationException exception) {
        final var complexResponse = new ComplexResponse<Void>(HttpStatus.BAD_REQUEST);
        exception.getConstraintViolations().forEach(error -> complexResponse
                .addError(new ComplexError(ExceptionCode.CONSTRAINT_VIOLATION.name(), error.getMessage(),
                        ErrorCode.UNKNOWN_ERROR_CODE.getCode())));
        return complexResponse;
    }

    @ResponseBody
    @ExceptionHandler(value = JsonParseException.class)
    public ComplexResponse<Void> handle(final JsonParseException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(HttpStatus.BAD_REQUEST, ExceptionCode.JSON_PARSE_ERROR.name(),
                ErrorCode.JSON_PARSING_ERROR, exception.getOriginalMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ComplexResponse<Void> handle(final HttpMessageNotReadableException exception) {
        return new ComplexResponse<>(HttpStatus.BAD_REQUEST, ExceptionCode.JSON_DESERIALIZATION_ERROR.name(),
                ErrorCode.HTTP_MESSAGE_NOT_READABLE, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ResourceAccessException.class)
    public ComplexResponse<Void> handle(final ResourceAccessException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(HttpStatus.SERVICE_UNAVAILABLE, ExceptionCode.RESOURCE_ACCESS_ERROR.name(),
                ErrorCode.RESOURCE_ACCESS_ERROR, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ComplexResponse<Void> handle(final MissingServletRequestParameterException exception) {
        return new ComplexResponse<>(HttpStatus.BAD_REQUEST, ExceptionCode.MISSING_PARAMETER.name(),
                ErrorCode.MISSING_REQUEST_PARAMETER, exception.getParameterType(), exception.getParameterName());
    }

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ComplexResponse<Void> handle(final HttpRequestMethodNotSupportedException exception) {
        return new ComplexResponse<>(HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.name(),
                ErrorCode.REQUEST_METHOD_NOT_SUPPORTED, exception.getMethod());
    }

    @ResponseBody
    @ExceptionHandler(value = DataAccessException.class)
    public ComplexResponse<Void> handle(final DataAccessException exception) {
        log.error(EXCEPTION_LABEL, exception);
        var description = exception.getMessage();
        final var firstCause = exception.getCause();
        if (firstCause != null && exception.getClass() != firstCause.getClass()) {
            description = firstCause.getMessage();
            final var secondCause = firstCause.getCause();
            if (secondCause != null && firstCause.getClass() != secondCause.getClass()) {
                description = secondCause.getMessage();
            }
        }
        return new ComplexResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionCode.DATA_ACCESS.name(),
                ErrorCode.DATA_ACCESS_ERROR, exception.getClass().getSimpleName(), description);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ComplexResponse<Void> defaultHandle(final Exception exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionCode.UNEXPECTED_OPERATION.name(),
                ErrorCode.UNHANDLED_ERROR, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ComplexResponse<Void> handle(final MethodArgumentTypeMismatchException exception) {
        return new ComplexResponse<>(HttpStatus.BAD_REQUEST, ExceptionCode.PARAMETER_FORMAT_ERROR.name(),
                ErrorCode.CONVERSION_ERROR, exception.getValue(),
                exception.getRequiredType() == null ? "" : exception.getRequiredType().getSimpleName());
    }
}
