/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.wave;

import waveclient.waveclient.events.Cancellable;
import waveclient.waveclient.utils.misc.input.KeyAction;
import net.minecraft.client.input.KeyInput;

public class KeyEvent extends Cancellable {
    private static final KeyEvent INSTANCE = new KeyEvent();

    public KeyInput input;
    public KeyAction action;

    public static KeyEvent get(KeyInput input, KeyAction action) {
        INSTANCE.setCancelled(false);
        INSTANCE.input = input;
        INSTANCE.action = action;
        return INSTANCE;
    }

    public int key() {
        return INSTANCE.input.key();
    }

    public int modifiers() {
        return INSTANCE.input.modifiers();
    }
}
