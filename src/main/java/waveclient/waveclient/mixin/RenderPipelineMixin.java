/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import waveclient.waveclient.mixininterface.IRenderPipeline;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RenderPipeline.class)
public abstract class RenderPipelineMixin implements IRenderPipeline {
    @Unique
    private boolean lineSmooth;

    @Override
    public void wave$setLineSmooth(boolean lineSmooth) {
        this.lineSmooth = lineSmooth;
    }

    @Override
    public boolean wave$getLineSmooth() {
        return lineSmooth;
    }
}
