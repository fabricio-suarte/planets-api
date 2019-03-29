package com.fabriciosuarte.planets.api.unitTests.mocks;

import com.fabriciosuarte.planets.api.common.ServiceLocator;

/**
 * Mocks the regular {@link import com.fabriciosuarte.planets.api.dataMapper.DataMapperLocator}
 */
public class DataMapperLocatorMock implements ServiceLocator {

    @Override
    @SuppressWarnings("unchecked")
    public <SInterface> SInterface getService(Class<SInterface> obj) {
        return (SInterface) new PlanetDataMapperMock();
    }
}
