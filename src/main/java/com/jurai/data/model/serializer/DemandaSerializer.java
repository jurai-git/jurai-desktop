package com.jurai.data.model.serializer;

import com.google.gson.*;
import com.jurai.data.AppState;
import com.jurai.data.model.Demanda;

import java.lang.reflect.Type;

public class DemandaSerializer implements JsonSerializer<Demanda>, JsonDeserializer<Demanda> {

    @Override
    public Demanda deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String foro = jsonObject.get("foro").getAsString();
        String competencia = jsonObject.get("competencia").getAsString();
        String classe = jsonObject.get("classe").getAsString();
        String assuntoPrincipal = jsonObject.get("assunto_principal").getAsString();
        boolean pedidoLiminar = jsonObject.get("pedido_liminar").getAsBoolean();
        boolean segJustica = jsonObject.get("segredo_justica").getAsBoolean();
        double valorAcao = jsonObject.get("valor_acao").getAsDouble();
        boolean dispensaLegal = jsonObject.get("dispensa_legal").getAsBoolean();
        boolean justicaGratuita = jsonObject.get("justica_gratuita").getAsBoolean();
        boolean guiaCustas = jsonObject.get("guia_custas").getAsBoolean();
        String resumo = jsonObject.get("resumo").getAsString();
        String statusDemanda = jsonObject.get("status").getAsString();
        String identificacao = jsonObject.get("identificacao").getAsString();
        String dono = jsonObject.get("dono").getAsString();

        return Demanda.getOrCreate(id, foro, competencia, classe, assuntoPrincipal, pedidoLiminar, segJustica, valorAcao, dispensaLegal, justicaGratuita, guiaCustas, resumo, statusDemanda, identificacao, dono);
    }

    @Override
    public JsonElement serialize(Demanda demanda, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("foro", demanda.getForo());
        result.addProperty("competencia", demanda.getCompetencia());
        result.addProperty("classe", demanda.getClasse());
        result.addProperty("assunto_principal", demanda.getAssuntoPrincipal());
        result.addProperty("pedido_liminar", demanda.isPedidoLiminar());
        result.addProperty("segredo_justica", demanda.isSegJustica());
        result.addProperty("valor_acao", demanda.getValorAcao());
        result.addProperty("dispensa_legal", demanda.isDispensaLegal());
        result.addProperty("justica_gratuita", demanda.isJusticaGratuita());
        result.addProperty("guia_custas", demanda.isGuiaCustas());
        result.addProperty("resumo", demanda.getResumo());
        result.addProperty("status", demanda.getStatusDemanda());
        result.addProperty("identificacao", demanda.getNome());
        result.addProperty("id_requerente", AppState.get().getSelectedRequerente().getIdRequerente());

        return result;
    }
}