/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.utils.misc.input;

import waveclient.waveclient.WaveClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(WaveClient.identifier("wave-client"));

    public static KeyBinding OPEN_GUI = new KeyBinding("key.wave-client.open-gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY);
    public static KeyBinding OPEN_COMMANDS = new KeyBinding("key.wave-client.open-commands", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, CATEGORY);

    private KeyBinds() {
    }

    public static KeyBinding[] apply(KeyBinding[] binds) {
        // Add key binding
        KeyBinding[] newBinds = new KeyBinding[binds.length + 2];

        System.arraycopy(binds, 0, newBinds, 0, binds.length);
        newBinds[binds.length] = OPEN_GUI;
        newBinds[binds.length + 1] = OPEN_COMMANDS;

        return newBinds;
    }
}
