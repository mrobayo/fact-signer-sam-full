package com.marvic.factsigner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvic.factsigner.model.comprobantes.types.Pago;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PagosListConverter implements AttributeConverter<List<Pago>, String> {

    Logger logger = LoggerFactory.getLogger(PagosListConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Pago> pagos) {
        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(pagos);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public List<Pago> convertToEntityAttribute(String pagosJSON) {
        List<Pago> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(pagosJSON, new TypeReference<List<Pago>>() {});
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return customerInfo;
    }
}
