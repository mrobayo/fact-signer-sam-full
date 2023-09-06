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
import java.util.Map;

public class PagosMapConverter implements AttributeConverter<Map<String, Pago>, String> {

    Logger logger = LoggerFactory.getLogger(PagosMapConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Pago> pagos) {
        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(pagos);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Pago> convertToEntityAttribute(String pagosJSON) {
        Map<String, Pago> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(pagosJSON, new TypeReference<HashMap<String, Pago>>() {});
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return customerInfo;
    }
}
