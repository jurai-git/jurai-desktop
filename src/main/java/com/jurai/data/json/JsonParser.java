package com.jurai.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jurai.data.model.Advogado;
import com.jurai.data.model.serializer.AdvogadoSerializer;
import com.jurai.util.EventLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class JsonParser {
    private final Gson gson;

    public JsonParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Advogado.class, new AdvogadoSerializer());
        gson = builder.create();
    }

    public Map<String, String> fromFile(Path file) throws IOException {
        try (var reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            EventLogger.logWarning("Failed to read JSON from file: " + file);
            throw new IOException("Failed to read JSON from file: " + file, e);
        }
    }

    public void toFile(Map<String, String> data, Path file) throws IOException {
        try {
            String json = gson.toJson(data, Map.class);
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            EventLogger.logWarning("Failed to write JSON to file: " + file);
            throw new IOException("Failed to write JSON to file: " + file, e);
        }
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public <T> String toJson(T data, Class<T> classOfT) {
        return gson.toJson(data, classOfT);
    }

}
