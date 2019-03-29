package com.fabriciosuarte.planets.api.application;

import com.fabriciosuarte.planets.api.dataMapper.DataMappers;
import com.fabriciosuarte.planets.api.dataMapper.PlanetDataMapper;
import com.fabriciosuarte.planets.api.domain.IdGenerator;
import com.fabriciosuarte.planets.api.domain.Planet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Represents the application service for Planet business transactions
 */
public final class PlanetsService {

    public static final int ERROR_CODE_PLANET_NOT_FOUND = 1002;
    public static final int ERROR_CODE_DUPLICATED_NAME = 1003;

    private PlanetDataMapper planetDataMapper = DataMappers.getPlanetDataMapper();

    private PlanetDTO toPlanetDTO(Planet obj) {
        return new PlanetDTO(
                obj.getId(),
                obj.getName(),
                obj.getClimate(),
                obj.getTerrain(),
                obj.getFilmsAppearances());
    }

    public PlanetDTO createPlanet(String name, String climate, String terrain) {

        Planet sameNamePlanet = this.planetDataMapper.selectByName(name);
        if(sameNamePlanet != null) {
            throw new ApplicationException(ERROR_CODE_DUPLICATED_NAME,
                    String.format("There is already a planet named '%s'", name));
        }

        long id = IdGenerator.getNewId();
        Planet newPlanet = Planet.create(id, name, climate, terrain);

        //Try to get the number of appearances in films...
        FilmsAppearanceService appearanceService = Services.getFilmsAppearanceService();

        CompletableFuture<FilmsAppearanceService.Result<Integer>> films
                =  appearanceService.requestPlanetAppearances(name);

        FilmsAppearanceService.Result<Integer> result = null;
        try {
            result = films.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(result != null) {

            if(result.isSuccessful()) {
                newPlanet.setFilmsAppearances(result.getValue());
            }
            else {
                System.err.println(result.getFailureReason());
            }
        }

        this.planetDataMapper.insert(newPlanet);

        return new PlanetDTO(id, name, climate, terrain, newPlanet.getFilmsAppearances());
    }

    public void deletePlanet(long id) {

        if(! this.planetDataMapper.exists(id)) {
            throw new ApplicationException(ERROR_CODE_PLANET_NOT_FOUND, "Planet not found!");
        }

        this.planetDataMapper.delete(id);
    }

    public PlanetDTO getPlanet(long id) {

        if(! this.planetDataMapper.exists(id)) {
            throw new ApplicationException(ERROR_CODE_PLANET_NOT_FOUND, "Planet not found!");
        }

        Planet planet = this.planetDataMapper.select(id);

        return this.toPlanetDTO(planet);
    }

    public PlanetDTO getPlanetByName(String name) {

        Planet planet = this.planetDataMapper.selectByName(name);

        if(planet == null)
            return null;

        return this.toPlanetDTO(planet);
    }

    public List<PlanetDTO> listPlanets() {

        List<Planet> planets = this.planetDataMapper.selectAll();

        List<PlanetDTO> result = new ArrayList<>();
        for(Planet p : planets) {
            result.add( this.toPlanetDTO(p));
        }

        return result;
    }

//    /*
//        This is method might be used to update the appearances in films of a given planet.
//     */
//    public void updatePlanet(long id) {
//
//        if(! this.planetDataMapper.exists(id)) {
//            throw new ApplicationException(ERROR_CODE_PLANET_NOT_FOUND, "Planet not found!");
//        }
//
//        Planet planet = this.planetDataMapper.select(id);
//
//        //Try to get the number of appearances in films...
//        FilmsAppearanceService appearanceService = Services.getFilmsAppearanceService();
//
//        CompletableFuture<FilmsAppearanceService.Result<Integer>> films
//                =  appearanceService.requestPlanetAppearances(planet.getName());
//
//        FilmsAppearanceService.Result<Integer> result = null;
//        try {
//            result = films.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        if(result != null) {
//
//            if(result.isSuccessful()) {
//                planet.setFilmsAppearances(result.getValue());
//            }
//            else {
//                System.err.println(result.getFailureReason());
//            }
//        }
//
//        this.planetDataMapper.update(planet);
//    }
}
