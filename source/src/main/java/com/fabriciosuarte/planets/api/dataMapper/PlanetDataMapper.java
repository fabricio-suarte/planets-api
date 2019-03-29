package com.fabriciosuarte.planets.api.dataMapper;

import com.fabriciosuarte.planets.api.domain.Planet;

import java.util.List;

public interface PlanetDataMapper {

    void insert(Planet obj);
    void update(Planet obj);
    void delete(long id);
    Planet select(long id);
    Planet selectByName(String name);
    List<Planet> selectAll();
    boolean exists(long id);

}
