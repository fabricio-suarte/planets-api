package com.fabriciosuarte.planets.api.common;

/**
 * Service locator interface
 */
public interface ServiceLocator {

    /**
     * Returns the implementation for the given service interface
     * @param <SInterface> The abstraction service interface
     * @param obj the service interface java class
     * @return SInterface service instance
     */
    <SInterface> SInterface getService(Class<SInterface> obj);
}

