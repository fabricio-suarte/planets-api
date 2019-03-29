package com.fabriciosuarte.planets.api.integrationTests;

public class PlanetDTO {
    public long id;
    public String name;
    public String climate;
    public String terrain;
    public Integer films;

    public PlanetDTO() {}

    public PlanetDTO(String name, String climate, String terrain) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }
}
