/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import net.minecraft.client.gl.Framebuffer;

public interface IWorldRenderer {
    void wave$pushEntityOutlineFramebuffer(Framebuffer framebuffer);

    void wave$popEntityOutlineFramebuffer();
}
