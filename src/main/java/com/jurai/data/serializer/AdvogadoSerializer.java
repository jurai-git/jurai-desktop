package com.jurai.data.serializer;

import com.google.gson.*;
import com.jurai.data.model.Advogado;

import java.lang.reflect.Type;

public class AdvogadoSerializer implements JsonSerializer<Advogado>, JsonDeserializer<Advogado> {
    @Override
    public JsonElement serialize(Advogado advogado, Type type, JsonSerializationContext jsonSerializationContext) throws JsonParseException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("username", advogado.getNome());
        jsonObject.addProperty("email", advogado.getEmail());
        jsonObject.addProperty("oab", advogado.getOab());
        jsonObject.addProperty("access_token", advogado.getAccessToken());

        return jsonObject;
    }

    @Override
    public Advogado deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String username = jsonObject.get("username").getAsString();
        String email = jsonObject.get("email").getAsString();
        String oab = jsonObject.get("oab").getAsString();
        String token = jsonObject.get("access_token").getAsString();

        return new Advogado(id, username, email, oab, token);
    }
}
