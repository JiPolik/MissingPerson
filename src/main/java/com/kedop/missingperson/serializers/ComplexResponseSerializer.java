package com.peratera.common.provision.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.peratera.common.provision.domain.ComplexResponse;
import com.peratera.common.provision.domain.ComplexResponse.JsonKeys;
import com.peratera.common.provision.domain.ErrorCode;
import com.peratera.common.provision.exceptions.PerateraSerializationException;
import java.io.IOException;
import org.springframework.http.HttpStatus;

/**
 * &copy; 2021-2022 Peratera. All rights reserved.<br/><br/>
 *
 * @author Dmitry Ionash <a href="mailto:idv@peratera.com">idv@peratera.com</a>, created 2021-Jun-21
 * @version 0.4.26
 * @since 1.0.0
 */
public class ComplexResponseSerializer extends JsonSerializer<ComplexResponse<?>> {

    @Override
    public void serialize(final ComplexResponse<?> value, final JsonGenerator generator,
            final SerializerProvider provider) throws IOException {
        if (value.isSuccess() == HttpStatus.valueOf(value.getStatus()).isError()) {
            throw new PerateraSerializationException(ErrorCode.INCORRECT_HTTP_STATUS);
        }
        if (value.isSuccess() && value.getErrors() != null) {
            throw new PerateraSerializationException(ErrorCode.UNEXPECTED_ERROR_ON_SUCCESS);
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
        generator.writeNumberField(JsonKeys.DURATION_VALUE, value.getDuration());
        generator.writeStringField(JsonKeys.PATH_VALUE, value.getPath());
        generator.writeStringField(JsonKeys.METHOD_VALUE, value.getMethod());
        generator.writeEndObject();
    }
}
