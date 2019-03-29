package com.fabriciosuarte.planets.api.dataMapper;

import com.fabriciosuarte.planets.api.common.ServiceLocator;

public class DataMapperLocator implements ServiceLocator {

    @Override
    @SuppressWarnings("unchecked")
    public <SInterface> SInterface getService(Class<SInterface> obj) {

        if(obj.equals(PlanetDataMapper.class)) {
            return (SInterface) new PlanetDataMapperImpl();
        }

        return null;
    }
}
