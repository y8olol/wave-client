/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.world;

import net.minecraft.block.BlockState;

public class BlockActivateEvent {
    private static final BlockActivateEvent INSTANCE = new BlockActivateEvent();

    public BlockState blockState;

    public static BlockActivateEvent get(BlockState blockState) {
        INSTANCE.blockState = blockState;
        return INSTANCE;
    }
}
