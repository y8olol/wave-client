/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import net.minecraft.client.world.ClientChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientChunkManager.class)
public interface ClientChunkManagerAccessor {
    @Accessor("chunks")
    ClientChunkManager.ClientChunkMap wave$getChunks();
}
