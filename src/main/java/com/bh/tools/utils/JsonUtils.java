package com.bh.tools.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author JanChao .
 */
public class JsonUtils {

    public static String toJSONString(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T fromJSONString(String JSONString, Class<T> c) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(JSONString, c);
    }
}
