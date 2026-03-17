/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import net.minecraft.client.resource.ResourceReloadLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ResourceReloadLogger.ReloadState.class)
public interface ReloadStateAccessor {
    @Accessor("finished")
    boolean wave$isFinished();
}
