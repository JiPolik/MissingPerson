package com.kedop.missingperson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kedop.missingperson.domain.ComplexResponse;
import com.kedop.missingperson.domain.ErrorCode;
import com.kedop.missingperson.exceptions.KedopSerializationException;
import java.io.IOException;
import org.springframework.http.HttpStatus;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
public class ComplexResponseSerializer extends JsonSerializer<ComplexResponse<?>> {

    @Override
    public void serialize(final ComplexResponse<?> value, final JsonGenerator generator,
            final SerializerProvider provider) throws IOException {
        if (value.isSuccess() == HttpStatus.valueOf(value.getStatus()).isError()) {
            throw new KedopSerializationException(ErrorCode.INCORRECT_HTTP_STATUS);
        }
        if (value.isSuccess() && value.getErrors() != null) {
            throw new KedopSerializationException(ErrorCode.UNEXPECTED_ERROR_ON_SUCCESS);
        }
        generator.writeStartObject();
        generator.writeNumberField(JsonKeys.STATUS_VALUE, value.getStatus());
        generator.writeBooleanField(JsonKeys.SUCCESS_VALUE, value.isSuccess());
        if (value.getErrors() != null) {
            generator.writeObjectField(JsonKeys.ERRORS_VALUE, value.getErrors());
        }
        if (value.getPayload() != null) {
            provider.defaultSerializeField(JsonKeys.PAYLOAD_VALUE, value.getPayload(), generator);
        } else if (value.isSuccess()) {
            generator.writeObjectFieldStart(JsonKeys.PAYLOAD_VALUE);
            generator.writeEndObject();
        }
        generator.writeEndObject();
    }

    public static class JsonKeys {

        public static final String STATUS_VALUE = "status";
        public static final String SUCCESS_VALUE = "success";
        public static final String DURATION_VALUE = "duration";
        public static final String ERRORS_VALUE = "errors";
        public static final String PAYLOAD_VALUE = "payload";
        public static final String PATH_VALUE = "path";
        public static final String METHOD_VALUE = "method";
    }
}
