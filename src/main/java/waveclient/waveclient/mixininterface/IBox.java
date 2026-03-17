/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import net.minecraft.util.math.BlockPos;

public interface IBox {
    void wave$expand(double v);

    void wave$set(double x1, double y1, double z1, double x2, double y2, double z2);

    default void wave$set(BlockPos pos) {
        wave$set(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }
}
