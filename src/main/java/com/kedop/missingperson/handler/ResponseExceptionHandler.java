package com.peratera.api.admin.handlers;

import static com.peratera.common.provision.ProvisionConfig.EXCEPTION_LABEL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.peratera.common.provision.domain.ComplexError;
import com.peratera.common.provision.domain.ComplexResponse;
import com.peratera.common.provision.domain.ErrorCode;
import com.peratera.common.provision.domain.GlobalValidationError;
import com.peratera.common.provision.exceptions.AdminException;
import com.peratera.common.provision.exceptions.CustomValidationException;
import com.peratera.common.provision.exceptions.CustomerException;
import com.peratera.common.provision.exceptions.ExceptionCode;
import com.peratera.common.provision.exceptions.RepeaterException;
import com.peratera.common.provision.exceptions.UnexpectedErrorException;
import com.peratera.common.provision.exceptions.auth.CustomOAuth2Exception;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * &copy; 2021-2022 Peratera. All rights reserved.<br/><br/>
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Aug-06
 * @version 0.4.18
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = RepeaterException.class)
    public ComplexResponse<Void> handle(final RepeaterException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(exception.getHttpStatus(), exception.getErrors().toArray(new ComplexError[]{}));
    }

    @ResponseBody
    @ExceptionHandler(value = AdminException.class)
    public ComplexResponse<Void> handle(final AdminException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(exception.getHttpStatus(), new ComplexError(exception.getPerateraErrorCode(),
            exception.getMessage(), exception.getErrorCode().getCode(), exception.getObjects()));
    }

    @ResponseBody
    @ExceptionHandler(value = CustomerException.class)
    public ComplexResponse<Void> handle(final CustomerException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(exception.getHttpStatus(), new ComplexError(exception.getPerateraErrorCode(),
                exception.getMessage(), exception.getErrorCode().getCode(), exception.getObjects()));
    }

    @ResponseBody
    @ExceptionHandler(value = CustomOAuth2Exception.class)
    public ComplexResponse<Void> handle(final CustomOAuth2Exception exception) {
        log.error(EXCEPTION_LABEL, exception);
        var errorResponse = new ComplexResponse<Void>(exception.getHttpErrorCode(), false);
        errorResponse.addError(new ComplexError(exception.getOAuth2ErrorCode(), exception.getMessage(),
                exception.getErrorCode().getCode(), exception.getObjects()));
        return errorResponse;
    }

    @ResponseBody
    @ExceptionHandler(value = UnexpectedErrorException.class)
    public ComplexResponse<Void> handle(final UnexpectedErrorException exception) {
        log.error(EXCEPTION_LABEL, exception);
        return new ComplexResponse<>(exception.getHttpStatus(), new ComplexError(exception.getPerateraErrorCode(),
            exception.getMessage(), exception.getErrorCode().getCode(), exception.getObjects()));
    }

    /**
     * According to {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter
     * #getArgumentsForConstraint} a first argument indicating the field name. Afterwards, it adds
     * all actual constraint annotation attributes (i.e. excluding "message", "groups" and "payload")
     * in alphabetical order of their attribute names.
     *
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

    @ResponseBody
    @ExceptionHandler(value = CustomValidationException.class)
    public ComplexResponse<Void> handle(final CustomValidationException exception) {
        var complexResponse = new ComplexResponse<Void>(HttpStatus.BAD_REQUEST);
        processFieldErrors(exception.getBindingResult().getFieldErrors(), complexResponse);
        processGlobalErrors(exception.getBindingResult().getGlobalErrors(), complexResponse);
        return complexResponse;
    }

    private static void processFieldErrors(final List<FieldError> fieldErrors,
            final ComplexResponse<Void> complexResponse) {
        fieldErrors.stream().filter(Objects::nonNull).forEach(error -> {
            var errorCode = ErrorCode.UNKNOWN_ERROR_CODE;
            final var arguments = error.getArguments();
            var objects = new Object[] {"unknown"};
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

    private static void processGlobalErrors(List<ObjectError> globalErrors, ComplexResponse<Void> complexResponse) {
        globalErrors.forEach(error -> {
            final var errorArguments = Objects.requireNonNull(error.getArguments());
            final var errorCode = (ErrorCode)  errorArguments[0];
            final var objects = Arrays.copyOfRange(errorArguments, 1, errorArguments.length);
            complexResponse.addError(new ComplexError(((GlobalValidationError) error).getGeneralErrorCode().name(),
                    errorCode, objects));
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
    @ExceptionHandler(value = MismatchedInputException.class)
    public ComplexResponse<Void> handle(final MismatchedInputException exception) {
        return new ComplexResponse<>(HttpStatus.BAD_REQUEST, ExceptionCode.JSON_DESERIALIZATION_ERROR.name(),
                ErrorCode.MISMATCHED_INPUT_ERROR, exception.getOriginalMessage());
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
    @ExceptionHandler(value = RestClientException.class)
    public ComplexResponse<Void> handle(final RestClientException exception) {
        log.error(EXCEPTION_LABEL, exception);
        var message = exception.getMessage();
        if (exception.getRootCause() != null) {
            final var cause = exception.getRootCause();
            if (cause instanceof JsonProcessingException) {
                message = ((JsonProcessingException) exception.getRootCause()).getOriginalMessage();
            } else {
                message = cause.getMessage();
            }
        }
        return new ComplexResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionCode.REST_CLIENT_ERROR.name(),
                ErrorCode.REST_CLIENT_ERROR, message);
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
    public ComplexResponse<Void> handle(final MethodArgumentTypeMismatchException  exception) {
        return new ComplexResponse<>(HttpStatus.BAD_REQUEST, ExceptionCode.PARAMETER_FORMAT_ERROR.name(),
                ErrorCode.CONVERSION_ERROR, exception.getValue(),
                exception.getRequiredType() == null ? "" : exception.getRequiredType().getSimpleName());
    }
}
