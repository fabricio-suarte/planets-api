package com.fabriciosuarte.planets.api.unitTests.mocks;

import com.fabriciosuarte.planets.api.common.ServiceLocator;

/**
 * Mocks the regular {@link com.fabriciosuarte.planets.api.application.ApplicationServiceLocator}
 */
@SuppressWarnings("unchecked")
public  class ApplicationServiceLocatorMock implements ServiceLocator {

    @Override
    public <SInterface> SInterface getService(Class<SInterface> obj) {
        return (SInterface) new SwapiServiceMock();
    }
}