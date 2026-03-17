/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.entity.player;

import waveclient.waveclient.events.Cancellable;
import net.minecraft.entity.Entity;

public class AttackEntityEvent extends Cancellable {
    private static final AttackEntityEvent INSTANCE = new AttackEntityEvent();

    public Entity entity;

    public static AttackEntityEvent get(Entity entity) {
        INSTANCE.setCancelled(false);
        INSTANCE.entity = entity;
        return INSTANCE;
    }
}
