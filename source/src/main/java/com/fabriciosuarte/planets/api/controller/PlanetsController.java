package com.fabriciosuarte.planets.api.controller;

import com.fabriciosuarte.planets.api.application.ApplicationException;
import com.fabriciosuarte.planets.api.application.PlanetDTO;
import com.fabriciosuarte.planets.api.application.PlanetsService;
import io.javalin.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Planets controller. Handles REST requests for "Planets" resource
 */
public class PlanetsController {

    private static final int BAD_REQUEST = 400;
    private static final int CONFLICT = 409;
    private static final int CREATED = 201;
    private static final int OK = 200;

    private static boolean isValidId(String stringId) {

        try {
            Long.parseLong(stringId);
            return true;
        }
        catch (NumberFormatException ignored) { }

        return false;
    }

    private static void setBadRequest(Context ctx, String message) {
        ctx.status(BAD_REQUEST);
        ctx.result(message);
    }

    public static void getAllPlanets(Context ctx) {
        PlanetsService service = new PlanetsService();

        List<PlanetDTO> planets = service.listPlanets();
        ctx.json(planets);
    }

    public static void getPlanetByName(Context ctx) {
        String name = ctx.pathParam("name");

        if(name.trim().isEmpty()) {
            setBadRequest(ctx, "invalid planet name");
            return;
        }

        try {
            name = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            setBadRequest(ctx, "it was not possible to decode the given planet name!");
            return;
        }

        PlanetsService service = new PlanetsService();

        PlanetDTO planet = service.getPlanetByName(name);
        ctx.json(planet == null ? new Object() : planet);
    }

    public static void getPlanet(Context ctx) {
        String stringId = ctx.pathParam("id");

        if(!isValidId(stringId)) {
            setBadRequest(ctx, "invalid planet id");
            return;
        }

        long id = Long.valueOf(stringId);

        PlanetsService service = new PlanetsService();

        try {
            PlanetDTO planet = service.getPlanet(id);
            ctx.json(planet);
        }
        catch (ApplicationException ex) {
            ctx.status(CONFLICT);
            ctx.json(new ApplicationError(ex.getCode(), ex.getMessage()));
        }
    }

    public static void createPlanet(Context ctx) {
        PlanetDTO planet  = ctx.bodyAsClass(PlanetDTO.class);
        PlanetsService service = new PlanetsService();

        try {
            planet = service.createPlanet(planet.name, planet.climate, planet.terrain);

            ctx.status(CREATED);
            ctx.json(planet);
        }
        catch (ApplicationException ex) {
            ctx.status(CONFLICT);
            ctx.json( new ApplicationError(ex.getCode(), ex.getMessage()));
        }
    }

    public static void deletePlanet(Context ctx) {

        String stringId = ctx.pathParam("id");

        if(!isValidId(stringId)) {
            setBadRequest(ctx, "invalid planet id");
            return;
        }

        long id = Long.valueOf(stringId);

        PlanetsService service = new PlanetsService();

        try {
            service.deletePlanet(id);
            ctx.status(OK);
            ctx.result("planet was successfully deleted");
        }
        catch (ApplicationException ex) {
            ctx.status(CONFLICT);
            ctx.json( new ApplicationError(ex.getCode(), ex.getMessage()));
        }
    }
}
