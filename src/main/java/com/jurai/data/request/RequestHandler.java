package com.jurai.data.request;

import com.google.gson.JsonObject;
import com.jurai.data.util.JsonUtils;
import com.jurai.util.EventLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestHandler {
    private String baseUrl;

    public RequestHandler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public JsonObject post(String endpoint, JsonObject body) throws ResponseNotOkException {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            return makeRequest(con, body);
        } catch (ResponseNotOkException e) {
            throw e;
        } catch(Exception e) {
            throw new ResponseNotOkException(500);
        }
    }
    public JsonObject put(String endpoint, JsonObject body) throws ResponseNotOkException {
        try {
            URL url = new URL(baseUrl + endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            return makeRequest(con, body);
        } catch (ResponseNotOkException e) {
            throw e;
        } catch(Exception e) {
            throw new ResponseNotOkException(500);
        }
    }

    private JsonObject makeRequest(HttpURLConnection con, JsonObject body) throws ResponseNotOkException, IOException {

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        EventLogger.log("request code: " + code);
        if (code/100 != 2) {
            throw new ResponseNotOkException(code);
        }
        
        JsonObject parsedResponse;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            parsedResponse = JsonUtils.asJson(response.toString());
        }

        con.disconnect();
        return parsedResponse;
    }

}
