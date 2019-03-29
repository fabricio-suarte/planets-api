package com.fabriciosuarte.planets.api.domain;

import java.util.Objects;

/**
 * Represents a planet
 */
public class Planet {
    //region attributes

    private long id;
    private String name;
    private String climate;
    private String terrain;

    //this is the number of appearances in filmsAppearances
    private Integer filmsAppearances;

    //endregion

    //constructor

    private Planet() {}

    //endregion

    //region public interface

    public long getId()  {return this.id; }
    public String getName() { return this.name; }
    public String getClimate() { return this.climate; }
    public String getTerrain() { return this.terrain; }
    public Integer getFilmsAppearances() { return this.filmsAppearances; }

    public void changeName (String newName) {
        Objects.requireNonNull(newName, "'newName' cannot be null or empty!");

        this.name = newName;
    }

    public void changeClimate (String newClimate) {
        Objects.requireNonNull(newClimate, "'newClimate' cannot be null or empty!");

        this.climate = newClimate;
    }

    public void changeTerrain (String newTerrain) {
        Objects.requireNonNull(newTerrain, "'newTerrain' cannot be null or empty!");

        this.terrain = newTerrain;
    }

    public void setFilmsAppearances(Integer films) {
        Objects.requireNonNull(films, "'films' cannot be null!");

        this.filmsAppearances = films;
    }

    public static Planet create(long id, String name, String climate, String terrain) {
        Objects.requireNonNull(name, "'name' cannot be null or empty!");
        Objects.requireNonNull(climate, "'climate' cannot be null or empty!");
        Objects.requireNonNull(terrain, "'terrain' cannot be null or empty!");

        Planet newPlanet = new Planet();
        newPlanet.id = id;
        newPlanet.name = name;
        newPlanet.climate = climate;
        newPlanet.terrain = terrain;

        return newPlanet;
    }

    //endregion
}
