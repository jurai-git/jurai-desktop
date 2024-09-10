package com.jurai.data.serializer;

import com.google.gson.*;
import com.jurai.data.model.Requerente;

import java.lang.reflect.Type;

public class RequerenteSerializer implements JsonSerializer<Requerente>, JsonDeserializer<Requerente> {

    @Override
    public Requerente deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Java JSON parsing code
        int idRequerente = jsonObject.get("id_requerente").getAsInt();
        boolean pessoaFisica = jsonObject.get("pessoa_fisica").getAsBoolean();
        String cpfCnpj = jsonObject.get("cpf_cnpj").getAsString();
        String nome = jsonObject.get("nome").getAsString();
        String nomeSocial = jsonObject.get("nome_social").getAsString();
        String genero = jsonObject.get("genero").getAsString();
        boolean idoso = jsonObject.get("idoso").getAsBoolean();
        String rg = jsonObject.get("rg").getAsString();
        String orgaoEmissor = jsonObject.get("orgao_emissor").getAsString();
        String estadoCivil = jsonObject.get("estado_civil").getAsString();
        String nacionalidade = jsonObject.get("nacionalidade").getAsString();
        String profissao = jsonObject.get("profissao").getAsString();
        String cep = jsonObject.get("cep").getAsString();
        String logradouro = jsonObject.get("logradouro").getAsString();
        String email = jsonObject.get("email").getAsString();
        String numImovel = jsonObject.get("num_imovel").getAsString();
        String complemento = jsonObject.get("complemento").getAsString();
        String estado = jsonObject.get("estado").getAsString();
        String bairro = jsonObject.get("bairro").getAsString();
        String cidade = jsonObject.get("cidade").getAsString();

        Requerente r = new Requerente(
                cpfCnpj, nome,
                nomeSocial, genero, idoso, rg, orgaoEmissor,
                estadoCivil, nacionalidade, profissao, cep,
                logradouro, email, numImovel, complemento, bairro, estado, cidade);
        r.setIdRequerente(idRequerente);

        return r;
    }

    @Override
    public JsonElement serialize(Requerente requerente, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();

        result.addProperty("id_requerente", requerente.getIdRequerente());
        result.addProperty("cpf_cnpj", requerente.getCpfCnpj());
        result.addProperty("nome", requerente.getNome());
        result.addProperty("nome_social", requerente.getNomeSocial());
        result.addProperty("genero", requerente.getGenero());
        result.addProperty("idoso", requerente.isIdoso());
        result.addProperty("rg", requerente.getRg());
        result.addProperty("orgao_emissor", requerente.getOrgaoEmissor());
        result.addProperty("estado_civil", requerente.getEstadoCivil());
        result.addProperty("nacionalidade", requerente.getNacionalidade());
        result.addProperty("profissao", requerente.getProfissao());
        result.addProperty("cep", requerente.getCep());
        result.addProperty("logradouro", requerente.getLogradouro());
        result.addProperty("email", requerente.getEmail());
        result.addProperty("num_imovel", requerente.getNumImovel());
        result.addProperty("complemento", requerente.getComplemento());
        result.addProperty("bairro", requerente.getBairro());
        result.addProperty("estado", requerente.getEstado());
        result.addProperty("cidade", requerente.getCidade());

        return result;

    }
}
