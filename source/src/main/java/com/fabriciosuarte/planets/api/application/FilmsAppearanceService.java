package com.fabriciosuarte.planets.api.application;

import java.util.concurrent.CompletableFuture;

/**
 * Application Business service interface for films appearance stuff
 */
public interface FilmsAppearanceService {

    interface Result<T> {
        boolean isSuccessful();
        String getFailureReason();
        T getValue();
    }

    CompletableFuture<Result<Integer>> requestPlanetAppearances(String planetName);

}
