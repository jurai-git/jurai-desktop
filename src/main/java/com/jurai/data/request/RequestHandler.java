package com.jurai.data.request;

import com.google.gson.JsonObject;
import com.jurai.data.AppState;
import com.jurai.data.json.JsonUtils;
import com.jurai.util.EventLogger;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.UUID;

public class RequestHandler {
    @Setter
    private String baseUrl;
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(AppState.get().getHttpTimeout()))
            .build();

    public RequestHandler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public JsonObject post(String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        return send("POST", endpoint, body, auth, false);
    }

    public JsonObject post(String endpoint) throws ResponseNotOkException {
        return send("POST", endpoint, null, null, false);
    }

    public JsonObject patch(String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        return send("PATCH", endpoint, body, auth, false);
    }

    public JsonObject delete(String endpoint, JsonObject body, String auth) throws ResponseNotOkException {
        return send("DELETE", endpoint, body, auth, false);
    }

    public JsonObject post(String endpoint, JsonObject body) throws ResponseNotOkException {
        return send("POST", endpoint, body, null, false);
    }

    public JsonObject patch(String endpoint, JsonObject body) throws ResponseNotOkException {
        return send("PATCH", endpoint, body, null, false);
    }

    public JsonObject delete(String endpoint, JsonObject body) throws ResponseNotOkException {
        return send("DELETE", endpoint, body, null, false);
    }

    public JsonObject get(String endpoint) throws ResponseNotOkException {
        return send("GET", endpoint, null, null, false);
    }

    public JsonObject get(String endpoint, String authHeader) throws ResponseNotOkException {
        return send("GET", endpoint, null, authHeader, false);
    }

    public JsonObject postFile(String endpoint, File file, String fieldName, String auth) throws ResponseNotOkException {
        try {
            String boundary = UUID.randomUUID().toString();
            String CRLF = "\r\n";
            String fileName = file.getName();
            String mimeType = Files.probeContentType(file.toPath());

            StringBuilder sb = new StringBuilder();
            sb.append("--").append(boundary).append(CRLF);
            sb.append("Content-Disposition: form-data; name=\"")
                    .append(fieldName).append("\"; filename=\"")
                    .append(fileName).append("\"").append(CRLF);
            sb.append("Content-Type: ").append(mimeType != null ? mimeType : "application/octet-stream").append(CRLF);
            sb.append(CRLF);

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            byte[] preamble = sb.toString().getBytes(StandardCharsets.UTF_8);
            byte[] closing = (CRLF + "--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8);

            byte[] multipartData = new byte[preamble.length + fileBytes.length + closing.length];
            System.arraycopy(preamble, 0, multipartData, 0, preamble.length);
            System.arraycopy(fileBytes, 0, multipartData, preamble.length, fileBytes.length);
            System.arraycopy(closing, 0, multipartData, preamble.length + fileBytes.length, closing.length);

            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .timeout(Duration.ofSeconds(AppState.get().getHttpTimeout()))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(multipartData));

            if (auth != null) {
                builder.header("Authorization", auth);
            }

            HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            int code = response.statusCode();
            EventLogger.log("upload response code: " + code);

            if (code / 100 != 2) {
                throw new ResponseNotOkException(code);
            }

            return JsonUtils.asJson(response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ResponseNotOkException(500);
        }
    }


    private JsonObject send(String method, String endpoint, JsonObject body, String auth, boolean retrying) throws ResponseNotOkException {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .timeout(Duration.ofSeconds(AppState.get().getHttpTimeout()))
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
            if (!retrying) {
                EventLogger.logWarning("Network error on RequestHandler; silently retrying. Error: " + e.getMessage());
                return send(method, endpoint, body, auth, true);
            } else {
                EventLogger.logWarning("Retry failed. Error: " + e.getMessage());
                e.printStackTrace();
                throw new ResponseNotOkException(InternalErrorCodes.NETWORK_ERROR);
            }
        }
    }
}
