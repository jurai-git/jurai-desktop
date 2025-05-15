package com.jurai.data.request;

import com.google.gson.JsonObject;
import com.jurai.data.json.JsonUtils;
import com.jurai.util.EventLogger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class RequestHandler {
    private String baseUrl;
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public RequestHandler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public JsonObject post(String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        return send("POST", endpoint, body, auth);
    }

    public JsonObject patch(String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        return send("PATCH", endpoint, body, auth);
    }

    public JsonObject delete(String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        return send("DELETE", endpoint, body, auth);
    }

    public JsonObject post(String endpoint, JsonObject body) throws ResponseNotOkException {
        return send("POST", endpoint, body, null);
    }

    public JsonObject patch(String endpoint, JsonObject body) throws ResponseNotOkException {
        return send("PATCH", endpoint, body, null);
    }

    public JsonObject delete(String endpoint, JsonObject body) throws ResponseNotOkException {
        return send("DELETE", endpoint, body, null);
    }

    public JsonObject get(String endpoint) throws ResponseNotOkException {
        return send("GET", endpoint, null, null);
    }

    public JsonObject get(String endpoint, String authHeader) throws ResponseNotOkException {
        return send("GET", endpoint, null, authHeader);
    }

    private JsonObject send(String method, String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .timeout(Duration.ofSeconds(5))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");

            if (auth != null) {
                builder.header("Authorization", auth);
            }

            if (body != null) {
                builder.method(method, HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8));
            } else {
                builder.method(method, HttpRequest.BodyPublishers.noBody());
            }

            HttpRequest request = builder.build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            int code = response.statusCode();
            EventLogger.log("request code: " + code);

            if (code / 100 != 2) {
                throw new ResponseNotOkException(code);
            }

            return JsonUtils.asJson(response.body());

        } catch (ResponseNotOkException e) {
            throw e;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ResponseNotOkException(500);
        }
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
