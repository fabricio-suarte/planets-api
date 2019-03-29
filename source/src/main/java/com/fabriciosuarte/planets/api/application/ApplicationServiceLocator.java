package com.fabriciosuarte.planets.api.application;

import com.fabriciosuarte.planets.api.common.ServiceLocator;

/**
 * Application business service locator
 */
public class ApplicationServiceLocator implements ServiceLocator {

    @Override
    @SuppressWarnings("unchecked")
    public <SInterface> SInterface getService(Class<SInterface> obj) {

        if(obj.equals(FilmsAppearanceService.class)) {
            return (SInterface) new SwapiService();
        }

        return null;
    }
}
