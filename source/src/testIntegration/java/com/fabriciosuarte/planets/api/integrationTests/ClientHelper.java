package com.fabriciosuarte.planets.api.integrationTests;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Helper to consume / test Planets API
 */
final class ClientHelper {

    private String uri;
    private Client client;
    private WebTarget baseTarget;

    private Invocation.Builder getBuilder(String path) {
        WebTarget target = this.baseTarget.path(path);
        return target.request(MediaType.APPLICATION_JSON);
    }

    ClientHelper(String uri) {
        this.uri = uri;
        this.client = ClientBuilder.newClient();
        this.baseTarget = client.target(this.uri);
    }

    <E> Response post(String path, E dto) {

        return this.getBuilder(path)
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON));
    }

    Response get(String path) {
        return this.getBuilder(path)
                .get();
    }

    Response delete(String path) {
        return this.getBuilder(path)
                .delete();
    }

}
