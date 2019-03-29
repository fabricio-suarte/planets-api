package com.fabriciosuarte.planets.api.unitTests;

import com.fabriciosuarte.planets.api.domain.IdGenerator;
import com.fabriciosuarte.planets.api.domain.Planet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlanetUnitTest {

    private static Planet defaultForChangeTests;

    //region setup and cleanUp

    @BeforeClass
    public static void setUp() {

        long id = IdGenerator.getNewId();
        String name = "Tatooine";
        String climate = "arid";
        String terrain = "desert";

        defaultForChangeTests = Planet.create(id, name, climate, terrain);
    }

    @AfterClass
    public static void cleanUp() {
        defaultForChangeTests = null;
    }

    //endregion

    //region creation tests

    @Test
    public void createSuccessfully() {
        long id = IdGenerator.getNewId();
        String name = "Tatooine";
        String climate = "arid";
        String terrain = "desert";

        Planet planet = Planet.create(id, name, climate, terrain);

        assertNotNull("no instance returned!", planet);
        assertEquals("id does not match!", id, planet.getId());
        assertEquals("name does not match!", name, planet.getName());
        assertEquals("climate does not match!", climate, planet.getClimate());
        assertEquals("terrain does not match!", terrain, planet.getTerrain());
    }

    @Test
    public void createWithoutFilmsAppearancesSuccessfully() {
        long id = IdGenerator.getNewId();
        String name = "Tatooine";
        String climate = "arid";
        String terrain = "desert";

        Planet planet = Planet.create(id, name, climate, terrain);

        assertNotNull("no instance returned!", planet);
        assertEquals("id does not match!", id, planet.getId());
        assertEquals("name does not match!", name, planet.getName());
        assertEquals("climate does not match!", climate, planet.getClimate());
        assertEquals("terrain does not match!", terrain, planet.getTerrain());
    }

    @Test(expected = NullPointerException.class)
    public void createInvalidNameException() {
        long id = IdGenerator.getNewId();
        String climate = "arid";
        String terrain = "desert";

        Planet.create(id, null, climate, terrain);
    }

    @Test(expected = NullPointerException.class)
    public void createInvalidClimateException() {
        long id = IdGenerator.getNewId();
        String name = "Tatooine";
        String terrain = "desert";

        Planet.create(id, name, null, terrain);
    }

    @Test(expected = NullPointerException.class)
    public void createInvalidTerrainException() {
        long id = IdGenerator.getNewId();
        String name = "Tatooine";
        String climate = "arid";

        Planet.create(id, name, climate, null);
    }

    //endregion

    //region changes tests

    @Test
    public void changeNameSuccessfully() {
        String newName = "New Tattoine";
        defaultForChangeTests.changeName(newName);

        assertEquals("newName does not match!", newName, defaultForChangeTests.getName());
    }

    @Test
    public void changeClimateSuccessfully() {
        String newClimate = "New arid";
        defaultForChangeTests.changeClimate(newClimate);

        assertEquals("newClimate does not match!", newClimate, defaultForChangeTests.getClimate());
    }

    @Test
    public void changeTerrainSuccessfully() {
        String newTerrain = "New arid";
        defaultForChangeTests.changeTerrain(newTerrain);

        assertEquals("newTerrain does not match!", newTerrain, defaultForChangeTests.getClimate());
    }

    @Test
    public void setFilmsAppearancesSuccessfully() {
        Integer newFilmsAppearances = 10;
        defaultForChangeTests.setFilmsAppearances(newFilmsAppearances);

        assertEquals("newFilmsAppearances does not match!", newFilmsAppearances, defaultForChangeTests.getFilmsAppearances());
    }

    @Test (expected = NullPointerException.class)
    public void changeNameInvalidNewDataException() {
        defaultForChangeTests.changeName(null);
    }

    @Test (expected = NullPointerException.class)
    public void changeClimateInvalidNewDataException() {
        defaultForChangeTests.changeClimate(null);
    }

    @Test (expected = NullPointerException.class)
    public void changeTerrainInvalidNewDataException() {
        defaultForChangeTests.changeTerrain(null);
    }

    @Test (expected = NullPointerException.class)
    public void setFilmsAppearanceInvalidNewDataException() {
        defaultForChangeTests.setFilmsAppearances(null);
    }

    //endregion

}
