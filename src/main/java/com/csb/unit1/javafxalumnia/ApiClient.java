package com.csb.unit1.javafxalumnia;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Map;
import java.util.HashMap;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8081/api";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> login(String username, String password) {
        try {
            String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return mapper.readValue(response.body(), Map.class);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error de conexión con el servidor");
            return error;
        }
    }

    // ✅ NUEVO MÉTODO POST
    public static String post(String endpoint, String jsonBody) {
        try {
            System.out.println("🚀 POST: " + BASE_URL + endpoint);
            System.out.println("📦 Body: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📡 Código: " + response.statusCode());
            return response.body();

        } catch (Exception e) {
            System.out.println("❌ ERROR POST " + endpoint + ": " + e.getMessage());
            return "{}";
        }
    }

    public static String get(String endpoint) {
        try {
            System.out.println("🚀 GET: " + BASE_URL + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📡 Código: " + response.statusCode());
            return response.body();

        } catch (Exception e) {
            System.out.println("❌ ERROR GET " + endpoint + ": " + e.getMessage());
            return "[]";
        }
    }

    public static String put(String endpoint, String jsonBody) {
        try {
            System.out.println("🚀 PUT: " + BASE_URL + endpoint);
            System.out.println("📦 Body: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📡 Código: " + response.statusCode());
            return response.body();

        } catch (Exception e) {
            System.out.println("❌ ERROR PUT " + endpoint + ": " + e.getMessage());
            return "{}";
        }
    }

    public static String delete(String endpoint) {
        try {
            System.out.println("🚀 DELETE: " + BASE_URL + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📡 Código: " + response.statusCode());
            return response.body();

        } catch (Exception e) {
            System.out.println("❌ ERROR DELETE " + endpoint + ": " + e.getMessage());
            return "{}";
        }
    }
}