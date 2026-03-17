/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.player;

import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;

public class LiquidInteract extends Module {
    public LiquidInteract() {
        super(Categories.Player, "liquid-interact", "Allows you to interact with liquids.");
    }
}
