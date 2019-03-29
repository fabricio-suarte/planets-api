package com.fabriciosuarte.planets.api.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.util.UrlEncoded;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * Concrete implementation of {@link FilmsAppearanceService}
 */
class SwapiService implements FilmsAppearanceService {

    //region constants

    private static final String PLANETS_RESOURCE_TYPE = "planets";
    private static final String BASE_URL_MASK = "https://swapi.co/api/%s?search=%s";

    private static final int CONNECTION_TIMEOUT = 6000;
    private static final int READ_TIMEOUT = 5000;

    //endregion

    //region private aux methods

    private String doHttpGet(String resourceType, String name) throws IOException {

        String urlString = String.format(BASE_URL_MASK, resourceType, UrlEncoded.encodeString(name));
        URL url = new URL(urlString);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        //For this service, it is necessary to set the user agent... otherwise, you get a 403!
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:65.0) Gecko/20100101 Firefox/65.0");

        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);

        int status = conn.getResponseCode();

        if(status == 200) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content.toString();
        }

        return null;
    }

    private JsonObject getJson(String resourceType, String name) {

        String content;

        try {
            content = this.doHttpGet(resourceType, name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error: " + e.getMessage(), e);
        }

        if(content == null)
            return null;

        JsonParser parser = new JsonParser();

        return parser.parse(content).getAsJsonObject();
    }

    private Result<Integer> doExceptionally(Throwable ex) {
        return new Result<Integer>() {
            @Override
            public boolean isSuccessful() {
                return false;
            }

            @Override
            public String getFailureReason() {
                return ex.getMessage();
            }

            @Override
            public Integer getValue() {
                return -1;
            }
        };
    }

    //endregion

    //region public interface

    @Override
    public CompletableFuture<Result<Integer>> requestPlanetAppearances(final String planetName) {

        CompletableFuture<JsonObject> getJsonFeature
                = CompletableFuture.supplyAsync( () -> this.getJson(PLANETS_RESOURCE_TYPE, planetName));

        CompletableFuture<Result<Integer>> parseFilmsDataFuture
                = getJsonFeature.thenApply( (jsonObject) -> {

                    if(jsonObject == null) {
                        throw new IllegalStateException("an error occurred while accessing SWApi service:'jsonObject' returned as null");
                    }

                    JsonArray result = jsonObject.getAsJsonArray("results");

                    int films = 0;
                    if(result != null && result.size() > 0) {
                        films = result
                                .get(0)//It should return only the searched planet
                                .getAsJsonObject()
                                .getAsJsonArray("films")
                                .size();
                    }

                    final int valueOfFilms = films;

                    return new Result<Integer>() {
                        @Override
                        public boolean isSuccessful() {
                            return true;
                        }

                        @Override
                        public String getFailureReason() {
                            return null;
                        }

                        @Override
                        public Integer getValue() {
                            return valueOfFilms;
                        }
                    };

                });

        return parseFilmsDataFuture.exceptionally(this::doExceptionally);
    }

    //endregion
}
