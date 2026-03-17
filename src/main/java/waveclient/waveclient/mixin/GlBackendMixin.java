/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.blaze3d.systems.RenderPass;
import waveclient.waveclient.mixininterface.IGpuDevice;
import net.minecraft.client.gl.GlBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GlBackend.class)
public abstract class GlBackendMixin implements IGpuDevice {
    @Unique
    private int x, y, width, height;

    @Unique
    private boolean set;

    @Override
    public void wave$pushScissor(int x, int y, int width, int height) {
        if (set)
            throw new IllegalStateException("Currently there can only be one global scissor pushed");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        set = true;
    }

    @Override
    public void wave$popScissor() {
        if (!set)
            throw new IllegalStateException("No scissor pushed");

        set = false;
    }

    @Deprecated
    @Override
    public void wave$onCreateRenderPass(RenderPass pass) {
        if (set) {
            pass.enableScissor(x, y, width, height);
        }
    }
}
