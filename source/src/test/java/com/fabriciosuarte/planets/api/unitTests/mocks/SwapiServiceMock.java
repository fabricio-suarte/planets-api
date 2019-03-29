package com.fabriciosuarte.planets.api.unitTests.mocks;

import com.fabriciosuarte.planets.api.application.FilmsAppearanceService;
import com.fabriciosuarte.planets.api.common.ServiceLocator;

import java.util.concurrent.CompletableFuture;

/**
 * Mocks the regular implementation of {@link FilmsAppearanceService}
 */
public class SwapiServiceMock implements FilmsAppearanceService {

    @Override
    public CompletableFuture<Result<Integer>> requestPlanetAppearances(String planetName) {
        return CompletableFuture.completedFuture(new Result<Integer>() {
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
                return 1;
            }
        });
    }
}