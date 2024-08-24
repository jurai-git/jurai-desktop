package com.jurai.data.util;

import com.google.gson.*;
import com.jurai.data.model.Model;
import com.jurai.util.EventLogger;

import java.io.Serializable;

public class JsonUtils {

    public static JsonObject asJson(String jsonString) {
        return JsonParser.parseString(jsonString).getAsJsonObject();
    }

    public static JsonObject createFallbackJson(Exception e) {
        EventLogger.logError("JSON not parsed successfully. Falling back to default error json. Error: " + e.getMessage());
        return asJson("{\"message\": \"ERROR_JSON_PARSING\", \"error\": " + e.getMessage() + "}");
    }

    public static <T extends Model & Serializable> T mapResponseAsObject(JsonObject json, Class<T> classOfT, JsonSerializer<T> serializer) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(classOfT, serializer);
        Gson gson = builder.create();
        return gson.fromJson(json, classOfT);
    }

    public static <T extends Model & Serializable> JsonObject mapObjectAsJson(T object, Class<T> classOfT, JsonSerializer<T> serializer) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(classOfT, serializer);
        Gson gson = builder.create();
        return JsonUtils.asJson(gson.toJson(object));
    }
}
