/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.entity;

import net.minecraft.entity.vehicle.AbstractBoatEntity;

public class BoatMoveEvent {
    private static final BoatMoveEvent INSTANCE = new BoatMoveEvent();

    public AbstractBoatEntity boat;

    public static BoatMoveEvent get(AbstractBoatEntity entity) {
        INSTANCE.boat = entity;
        return INSTANCE;
    }
}
