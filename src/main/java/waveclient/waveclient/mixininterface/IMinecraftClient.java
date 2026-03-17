/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import net.minecraft.client.gl.Framebuffer;

public interface IMinecraftClient {
    void wave$rightClick();

    void wave$setFramebuffer(Framebuffer framebuffer);
}
