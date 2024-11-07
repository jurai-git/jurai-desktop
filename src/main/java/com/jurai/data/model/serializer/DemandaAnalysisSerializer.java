package com.jurai.data.model.serializer;

import com.google.gson.*;
import com.jurai.data.model.DemandaAnalysis;

import java.lang.reflect.Type;

public class DemandaAnalysisSerializer implements JsonDeserializer<DemandaAnalysis> {

    @Override
    public DemandaAnalysis deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject probabilities = jsonObject.get("probabilities").getAsJsonObject();
        String predicted = jsonObject.get("predicted_class").getAsString();
        double negative = probabilities.get("negative").getAsDouble();
        double partial = probabilities.get("partial").getAsDouble();
        double positive = probabilities.get("positive").getAsDouble();
        return new DemandaAnalysis(negative, partial, positive, predicted);
    }
}
