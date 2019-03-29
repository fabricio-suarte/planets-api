package com.fabriciosuarte.planets.api.unitTests;

import com.fabriciosuarte.planets.api.application.ApplicationException;
import com.fabriciosuarte.planets.api.application.PlanetDTO;
import com.fabriciosuarte.planets.api.application.PlanetsService;
import com.fabriciosuarte.planets.api.application.Services;
import com.fabriciosuarte.planets.api.dataMapper.DataMappers;
import com.fabriciosuarte.planets.api.unitTests.mocks.ApplicationServiceLocatorMock;
import com.fabriciosuarte.planets.api.unitTests.mocks.DataMapperLocatorMock;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlanetsServiceUnitTest {

    @BeforeClass
    public static void setUp() {

        //Mock setup
        Services.setLocator(new ApplicationServiceLocatorMock());
        DataMappers.setLocator(new DataMapperLocatorMock());
    }

    @Test
    public void createPlanetSuccessfully() {

        PlanetsService service = new PlanetsService();
        PlanetDTO planet = service.createPlanet("Planet A", "moist", "flat land");

        assertTrue("id should be greater than zero!", planet.id > 0);
    }

    @Test
    public void deletePlanetSuccessfully() {

        PlanetsService service = new PlanetsService();
        PlanetDTO planet = service.createPlanet("Planet to be Deleted", "moist", "flat land");

        service.deletePlanet(planet.id);

        assertTrue(true); // if no exception was launched, delete was ok
    }

    @Test
    public void getPlanetSuccessfully() {
        String name = "Planet to get";

        PlanetsService service = new PlanetsService();
        PlanetDTO createdPlanet = service.createPlanet(name, "moist", "flat land");

        PlanetDTO planet = service.getPlanet(createdPlanet.id);
        assertNotNull("returned planet data after creation is null!", planet);
        assertEquals("returned planet's id does not match!", createdPlanet.id, planet.id);
    }

    @Test
    public void getPlanetByNameSuccessfully() {
        String name = "Planet to get";

        PlanetsService service = new PlanetsService();
        service.createPlanet(name, "moist", "flat land");

        PlanetDTO planet = service.getPlanetByName(name);
        assertNotNull("returned planet data after creation is null!", planet);
        assertEquals("returned planet's name does not match!", name, planet.name);
    }

    //should return null (no exception) if you pass a name that does not exist
    @Test
    public void getPlanetByNameNotExistsSuccessfully() {
        String name = "Planet name that does not exist";

        PlanetsService service = new PlanetsService();

        PlanetDTO planet = service.getPlanetByName(name);
        assertNull("returned planet should be null because its name does not exist! ", planet);

    }

    @Test
    public void listPlanetsSuccessfully() {
        PlanetsService service = new PlanetsService();

        service.createPlanet("Planet A", "x", "y");
        service.createPlanet("Planet B", "x", "y");
        service.createPlanet("Planet C", "x", "y");

        int availablePlanets = service.listPlanets().size();

        assertEquals("invalid number of planets was returned!", 3, availablePlanets);
    }

    @Test
    public void deletePlanetNotExistException() {

        PlanetsService service = new PlanetsService();

        ApplicationException ex = null;
        try {
            service.deletePlanet(100);
        }
        catch (ApplicationException e) { ex = e;}

        assertNotNull("no application exception was raised!", ex);
        assertEquals("error code does not math!", PlanetsService.ERROR_CODE_PLANET_NOT_FOUND, ex.getCode());
    }

    @Test
    public void getPlanetNotExistException() {

        PlanetsService service = new PlanetsService();

        ApplicationException ex = null;
        try {
            service.getPlanet(100);
        }
        catch (ApplicationException e) { ex = e;}

        assertNotNull("no application exception was raised!", ex);
        assertEquals("error code does not math!", PlanetsService.ERROR_CODE_PLANET_NOT_FOUND, ex.getCode());
    }

    @Test
    public void createPlanetDuplicatedNameException() {

        PlanetsService service = new PlanetsService();

        service.createPlanet("Planet A", "moist", "flat land");

        ApplicationException ex = null;
        try {
            service.createPlanet("Planet A", "arid", "mountain");
        }
        catch (ApplicationException e) { ex = e;}

        assertNotNull("no application exception was raised!", ex);
        assertEquals("error code does not math!", PlanetsService.ERROR_CODE_DUPLICATED_NAME, ex.getCode());
    }

}
