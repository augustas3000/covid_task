package com.interview.task.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.interview.task.CountryPOJOBasic;
import com.interview.task.DailyDataPOJO;

import java.util.List;
import java.io.IOException;

public class JsonHelper {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
//        make object mapper support new time classes like LocalDate:
        defaultObjectMapper.registerModule(new JavaTimeModule());
//        configurations:
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return object mapper:
        return defaultObjectMapper;
    }

//    --------------------------------------------------------------------------
//    Methods to familiarise with Jackson(Java JSON API):
    public static JsonNode fromJsonStringToJsonNode(String jsonString) throws IOException {
        return objectMapper.readTree(jsonString);
    }

    public static <A> A fromJsonNodeToPojo(JsonNode node, Class<A> classs) throws JsonProcessingException {

        return objectMapper.treeToValue(node, classs);
    }


    public static JsonNode fromPojoToJsonNode(Object pojo) {
       return objectMapper.valueToTree(pojo);
    }

    public static String fromJsonNodeToJsonString(JsonNode node) throws JsonProcessingException {
        return generateString(node,false);
    }

    public static String fromJsonNodeToJsonStringIndented(JsonNode node) throws JsonProcessingException {
        return generateString(node,true);
    }


    private static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if( pretty )
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(node);
    }

//    --------------------------------------------------------------------------
//    Methods used to complete the exercise:
//    the method will take in a string of jsonArray
//    and return an array of CountryPOJOS

    public static List<CountryPOJOBasic> fromJsonArrayToPojosArrayCOUNTRY(String jsonArray) throws IOException {
//        return objectMapper.readValue(jsonArray, CountryPOJO[].class);
        return objectMapper.readValue(jsonArray, new TypeReference<List<CountryPOJOBasic>>(){});
    }

    public static List<DailyDataPOJO> fromJsonArrayToPojosArrayDAILY(String jsonArray) throws IOException {
        return objectMapper.readValue(jsonArray, new TypeReference<List<DailyDataPOJO>>(){});
    }

}
