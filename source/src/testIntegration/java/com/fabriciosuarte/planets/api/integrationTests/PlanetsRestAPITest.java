package com.fabriciosuarte.planets.api.integrationTests;

import org.eclipse.jetty.util.UrlEncoded;
import org.junit.Assume;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.Assert.*;

public class PlanetsRestAPITest {

    private static final int CREATED = 201;
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int CONFLICT = 409;

    private static final String SERVER = "http://localhost:7000";

    //region create planet tests

    @Test
    public void createPlanetSuccessfully() {

        PlanetDTO planet = new PlanetDTO("Planet test", "arid", "desert");

        ClientHelper helper = new ClientHelper(SERVER);
        Response response = helper.post("planets", planet);

        assertEquals("response code is not 'Created'!", CREATED, response.getStatus());

        PlanetDTO created = response.readEntity(PlanetDTO.class);

        assertNotNull("returned object is null or invalid!", created);
    }

    //Tatooine is planet that really exists in SWAPi
    @Test
    public void createPlanetTatooineSuccessfully() {

        PlanetDTO planet = new PlanetDTO("Tatooine", "arid", "desert");

        ClientHelper helper = new ClientHelper(SERVER);
        Response response = helper.post("planets", planet);

        assertEquals("response code is not 'Created'!", CREATED, response.getStatus());

        PlanetDTO created = response.readEntity(PlanetDTO.class);

        assertNotNull("returned object is null!", created);

        String message = "appearances in films was supposed to be set. Check the availability of SWAPI service";
        Assume.assumeTrue(message, created.films != null && created.films > 0);
    }

    @Test
    public void createPlanetAlreadyExistException() {
        PlanetDTO planetA = new PlanetDTO("Duplicated Planet", "arid", "desert");
        PlanetDTO planetB = new PlanetDTO( "Duplicated Planet", "temperate", "grasslands, mountains");

        ClientHelper helper = new ClientHelper(SERVER);

        helper.post("planets", planetA);
        Response response = helper.post("planets", planetB);

        assertEquals("response code is not 'Conflict'!", CONFLICT, response.getStatus());

        ApplicationErrorDTO errorDTO = response.readEntity(ApplicationErrorDTO.class);

        assertNotNull("returned object is null or invalid!", errorDTO);
    }

    //endregion

    //region get and list planets tests

    @Test
    public void getPlanetSuccessfully() {
        PlanetDTO planet = new PlanetDTO("Planet to be get", "arid", "desert");

        ClientHelper helper = new ClientHelper(SERVER);

        Response postResponse = helper.post("planets", planet);
        planet = postResponse.readEntity(PlanetDTO.class);

        String path = String.format("planets/%d", planet.id);
        Response getResponse = helper.get(path);

        assertEquals("response code is not 'OK'!", OK, getResponse.getStatus());

        PlanetDTO returnedPlanet = getResponse.readEntity(PlanetDTO.class);

        assertNotNull("returned object is null or invalid!", returnedPlanet);
    }

    @Test
    public void getPlanetByNameSuccessfully() {
        PlanetDTO planet = new PlanetDTO("Planet by name", "arid", "desert");

        ClientHelper helper = new ClientHelper(SERVER);

        Response postResponse = helper.post("planets", planet);
        planet = postResponse.readEntity(PlanetDTO.class);

        String path = String.format("planets/query/%s", UrlEncoded.encodeString(planet.name));
        Response getResponse = helper.get(path);

        assertEquals("response code is not 'OK'!", OK, getResponse.getStatus());

        PlanetDTO returnedPlanet = getResponse.readEntity(PlanetDTO.class);

        assertNotNull("returned object is null or invalid!", returnedPlanet);
    }

    @Test
    public void getPlanetIdDoesNotExist() {

        ClientHelper helper = new ClientHelper(SERVER);

        //1 as id.. must not exists
        String path = String.format("planets/%d", 1);
        Response response = helper.get(path);

        assertEquals("response code is not 'CONFLICT1!", CONFLICT, response.getStatus());

        ApplicationErrorDTO errorDTO = response.readEntity(ApplicationErrorDTO.class);

        assertNotNull("returned object is null or invalid!", errorDTO);
    }

    @Test
    public void getPlanetInvalidId() {

        ClientHelper helper = new ClientHelper(SERVER);

        String path = String.format("planets/%s", "234dsf");
        Response response = helper.get(path);

        assertEquals("response code is not 'BAD REQUEST'!", BAD_REQUEST, response.getStatus());
    }

    @Test
    public void listPlanetsSuccessfully() {
        PlanetDTO planet1 = new PlanetDTO("Planet l1", "arid", "desert");
        PlanetDTO planet2 = new PlanetDTO("Planet l2", "arid", "desert");

        ClientHelper helper = new ClientHelper(SERVER);

        //Adding planets
        helper.post("planets", planet1);
        helper.post("planets", planet2);

        Response getResponse = helper.get("planets");

        assertEquals("response code is not 'OK'!", OK, getResponse.getStatus());

        List<PlanetDTO> planetsList = getResponse.readEntity(new GenericType<List<PlanetDTO>>() {});

        assertNotNull("returned list is null or invalid!", planetsList);
        assertTrue( "returned list should returned at least two planets!", planetsList.size() >= 2);
    }

    //endregion

    //delete planet tests

    @Test
    public void deletePlanetSuccessfully() {
        PlanetDTO planet = new PlanetDTO("Planet to be deleted", "arid", "desert");

        ClientHelper helper = new ClientHelper(SERVER);

        Response postResponse = helper.post("planets", planet);
        planet = postResponse.readEntity(PlanetDTO.class);

        String path = String.format("planets/%d", planet.id);
        Response getResponse = helper.delete(path);

        assertEquals("response code is not 'OK'!", OK, getResponse.getStatus());
    }

    @Test
    public void deletePlanetIdDoesNotExist() {

        ClientHelper helper = new ClientHelper(SERVER);

        //1 as id.. must not exists
        String path = String.format("planets/%d", 1);
        Response response = helper.delete(path);

        assertEquals("response code is not 'CONFLICT'!", CONFLICT, response.getStatus());

        ApplicationErrorDTO errorDTO = response.readEntity(ApplicationErrorDTO.class);

        assertNotNull("returned object is null or invalid!", errorDTO);
    }

    @Test
    public void deletePlanetInvalidId() {

        ClientHelper helper = new ClientHelper(SERVER);

        String path = String.format("planets/%s", "234dsf");
        Response response = helper.delete(path);

        assertEquals("response code is not 'BAD REQUEST'!", BAD_REQUEST, response.getStatus());
    }

    //endregion

}
