package com.fabriciosuarte.planets.api.domain;

import java.util.UUID;

/**
 * Provides a simple approach for creating random long ids
 */
public final class IdGenerator {

    public static long getNewId() {
        long rawId = UUID.randomUUID().getLeastSignificantBits();

        //this AND bit operation is for ensuring positive long values
        return rawId & Long.MAX_VALUE;
    }
}
