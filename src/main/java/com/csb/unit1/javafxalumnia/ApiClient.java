package com.csb.unit1.javafxalumnia.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;

import java.io.InputStream;

/**
 * Cliente genérico para conectarse al backend alumnia-api.
 */
public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080/api"; // URL del backend
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClients.createDefault();

    public static <T> T get(String endpoint, Class<T> responseType) throws Exception {
        HttpGet request = new HttpGet(BASE_URL + endpoint);

        try (ClassicHttpResponse response = client.executeOpen(null, request, null)) {
            int status = response.getCode();
            if (status != 200) {
                throw new RuntimeException("Error: código HTTP " + status);
            }
            try (InputStream body = response.getEntity().getContent()) {
                return mapper.readValue(body, responseType);
            }
        }
    }
}

