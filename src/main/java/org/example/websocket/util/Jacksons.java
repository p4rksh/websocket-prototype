package org.example.websocket.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@UtilityClass
public class Jacksons {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATETIME_FORMATTER));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATETIME_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

        mapper.registerModule(module);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    private static final JsonMapper jsonMapper = new JsonMapper();

    static {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATETIME_FORMATTER));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATETIME_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

        jsonMapper.registerModule(module);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    public static String writeValueAsString(Object obj) {

        String result = null;

        try {
            if (null != obj) {
                result = getJsonMapper().writeValueAsString(obj);
            }
        } catch (IOException e) {
            log.warn("JSON serialize Error {}", obj);
        }

        return result;
    }

    public static <T> T readValue(String content, Class<T> tClass) {
        try {
            if (null != content) {
                return getJsonMapper().readValue(content, tClass);
            }
        } catch (IOException e) {
            log.warn("JSON deserialize Error {}", content);
        }

        return null;
    }

    public static <T> T readValue(byte[] content, Class<T> tClass) {


        try {
            if (null != content) {
                return getJsonMapper().readValue(content, tClass);
            }
        } catch (IOException e) {
            log.warn("JSON deserialize Error(Byte) {}", content);
        }

        return null;
    }
}