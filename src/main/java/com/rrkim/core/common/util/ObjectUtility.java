package com.rrkim.core.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectUtility {

    public static <T, R> R convertObject(T object, Class<R> returnClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(object, returnClass);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertObjectToMap(Object object) {
        if(object instanceof Collection<?>) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(object, HashMap.class);
    }

    public static <T> List<Map<String, Object>> convertObjectToMap(List<T> list) {
        if(list == null) {
            return null;
        }

        return list.stream().map(ObjectUtility::convertObjectToMap).toList();
    }

    public static <T> T convertJsonStringToObject(String jsonString, TypeReference<T> typeReference) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, typeReference);
    }

    public static String convertJsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static String[] getArray(List<String> list) {
        if(list == null) { return new String[0]; }
        return list.toArray(new String[0]);
    }

    public static <T, R> boolean isIdsMatchObjects(List<R> objectIds, List<T> objects, Function<? super T, ? extends R> mapper) {
        Set<R> mappedObjects = objects.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return mappedObjects.containsAll(objectIds);
    }

    public static <T, R> R getObject(T object, Function<? super T, ? extends R> mapper) {
        if(object == null) { return null; }
        return mapper.apply(object);
    }

    public static <T, R> List<R> getObjects(List<T> objects, Function<? super T, ? extends R> mapper) {
        if(objects == null) { return null; }
        List<R> objectIds = new ArrayList<>();

        for(T object : objects) {
            objectIds.add(getObject(object, mapper));
        }

        return objectIds;
    }
}
