/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.entity.player;

import waveclient.waveclient.events.Cancellable;

public class DoAttackEvent extends Cancellable {
    private static final DoAttackEvent INSTANCE = new DoAttackEvent();

    public static DoAttackEvent get() {
        return INSTANCE;
    }
}
