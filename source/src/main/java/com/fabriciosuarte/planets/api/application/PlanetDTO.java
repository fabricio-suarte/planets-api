package com.fabriciosuarte.planets.api.application;

public final class PlanetDTO {
    public long id;
    public String name;
    public String climate;
    public String terrain;
    public Integer films;

    public PlanetDTO(long id, String name, String climate, String terrain, Integer films) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.films = films;
    }
}
