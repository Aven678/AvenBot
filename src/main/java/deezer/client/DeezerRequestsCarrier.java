package deezer.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

class DeezerRequestsCarrier {

    private static final String DEEZER_API_URL = "http://api.deezer.com";

    private Gson mapper;

    DeezerRequestsCarrier() {
        this.mapper = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    <T> T get(final String object,
              Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object,
              final int index, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "?index=" + index;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object,
              final int index, final int limit, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "?index=" + index + "&limit=" + limit;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final String method,
              Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + method;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final String method,
              final int index, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + method + "?index=" + index;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final String method,
              final int index, final int limit, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + method + "?index=" + index + "&limit=" + limit;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final Long id,
              Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + id;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final Long id,
              final int index, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + id + "?index=" + index;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final Long id,
              final int index, final int limit, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + id + "?index=" + index + "&limit=" + limit;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final Long id, final String method,
              Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + id + "/" + method;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final Long id, final String method,
              final int index, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + id + "/" + method + "?index=" + index;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final Long id, final String method,
              final int index, final int limit, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + id + "/" + method + "?index=" + index + "&limit=" + limit;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final String method,
              final String query, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + method + "?q=" + query;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final String method,
              final String query, final int index, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + method + "?q=" + query + "&index=" + index;
        return this.getData(url, targetClass);
    }

    <T> T get(final String object, final String method,
              final String query, final int index, final int limit, Class<T> targetClass) {
        final String url =
                DeezerRequestsCarrier.DEEZER_API_URL + "/" + object + "/" + method + "?q=" + query + "&index=" + index + "&limit=" + limit;
        return this.getData(url, targetClass);
    }

    <T> T getData(final String url, Class<T> targetClass) {
        try {
            final String response = this.requestData(url);
            if (response.startsWith("{\"error")) {
                JsonObject errorObject = this.mapper.fromJson(response, JsonObject.class);
                DeezerClientException.Error error =
                        this.mapper.fromJson(errorObject.get("error"), DeezerClientException.Error.class);
                throw new DeezerClientException(url, error);
            }
            return this.mapper.fromJson(response, targetClass);
        } catch (DeezerClientException e) {
            throw e;
        } catch (Exception e) {
            throw new DeezerClientException(url, e);
        }
    }

    private String requestData(final String requestedUrl) throws IOException {
        URL url = new URL(requestedUrl);
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(url.openStream()))) {
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                resultBuilder.append(line);
            return resultBuilder.toString();
        }
    }

}
