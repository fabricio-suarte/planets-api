package com.fabriciosuarte.planets.api.unitTests.mocks;

import com.fabriciosuarte.planets.api.dataMapper.PlanetDataMapper;
import com.fabriciosuarte.planets.api.domain.Planet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Mocks the regular implementation of {@link PlanetDataMapper}
 */
public class PlanetDataMapperMock implements PlanetDataMapper {

    private HashMap<Long, Planet> data = new HashMap<>();

    @Override
    public void insert(Planet obj) {
        data.put(obj.getId(), obj);
    }

    @Override
    public void update(Planet obj) {
        data.put(obj.getId(), obj);
    }

    @Override
    public void delete(long id) {
        data.remove(id);
    }

    @Override
    public Planet select(long id) {
        return data.get(id);
    }

    @Override
    public Planet selectByName(String name) {
        for(Planet p : data.values()) {
            if(p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public List<Planet> selectAll() {

        return new ArrayList<>(data.values());
    }

    @Override
    public boolean exists(long id) {
        return data.containsKey(id);
    }
}