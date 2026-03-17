/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.entity.player;

import net.minecraft.util.hit.HitResult;

public class ItemUseCrosshairTargetEvent {
    private static final ItemUseCrosshairTargetEvent INSTANCE = new ItemUseCrosshairTargetEvent();

    public HitResult target;

    public static ItemUseCrosshairTargetEvent get(HitResult target) {
        INSTANCE.target = target;
        return INSTANCE;
    }
}
