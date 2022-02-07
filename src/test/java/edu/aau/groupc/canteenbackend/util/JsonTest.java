package edu.aau.groupc.canteenbackend.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

public interface JsonTest {

    ObjectMapper objectMapper = createObjectMapper();

    static ObjectMapper createObjectMapper() {
        ObjectMapper m = new ObjectMapper();
        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return m;
    }

    default void assertJsonObjectEquals(Object expected, Object actual, boolean strict) throws JSONException, JsonProcessingException {
        JSONObject expectedJsonObject = convertToJsonObject(expected);
        JSONObject actualJsonObject = convertToJsonObject(actual);
        JSONAssert.assertEquals(expectedJsonObject, actualJsonObject, strict);
    }

    default void assertJsonObjectEquals(Object expected, Object actual) throws JSONException, JsonProcessingException {
        assertJsonObjectEquals(expected, actual, false);
    }

    default void assertJsonArrayEquals(Object expected, Object actual, boolean strict) throws JSONException, JsonProcessingException {
        JSONArray expectedJsonArray = convertToJsonArray(expected);
        JSONArray actualJsonArray = convertToJsonArray(actual);
        JSONAssert.assertEquals(expectedJsonArray, actualJsonArray, strict);
    }

    default void assertJsonArrayEquals(Object expected, Object actual) throws JSONException, JsonProcessingException {
        assertJsonArrayEquals(expected, actual, false);
    }

    default JSONObject convertToJsonObject(Object o) throws JsonProcessingException, JSONException {
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        String json;
        if (!(o instanceof String)) {
            json = objectMapper.writeValueAsString(o);
        } else {
            json = (String) o;
        }
        return new JSONObject(json);
    }

    default JSONArray convertToJsonArray(Object o) throws JsonProcessingException, JSONException {
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        String json;
        if (!(o instanceof String)) {
            json = objectMapper.writeValueAsString(o);
        } else {
            json = (String) o;
        }
        return new JSONArray(json);
    }

    default String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
