package com.fabriciosuarte.planets.api.dataMapper;

import com.fabriciosuarte.planets.api.common.ServiceLocator;

/**
 * Helper, kind of wrapper for the data mapper service locator
 */
public final class DataMappers {

    private static ServiceLocator locator;

    private DataMappers() {}

    public static void setLocator(ServiceLocator obj) {
        locator = obj;
    }

    public static PlanetDataMapper getPlanetDataMapper() {
        return locator.getService(PlanetDataMapper.class);
    }
}
