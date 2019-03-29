package com.fabriciosuarte.planets.api.application;

import com.fabriciosuarte.planets.api.common.ServiceLocator;

/**
 * Helper, kind of wrapper for an application service locator
 */
public final class Services {

    private static ServiceLocator locator;

    private Services() {}

    public static void setLocator(ServiceLocator obj) {
        locator = obj;
    }

    public static FilmsAppearanceService getFilmsAppearanceService() {
        return locator.getService(FilmsAppearanceService.class);
    }

}
