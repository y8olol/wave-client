/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public interface IRaycastContext {
    void wave$set(Vec3d start, Vec3d end, RaycastContext.ShapeType shapeType, RaycastContext.FluidHandling fluidHandling, Entity entity);
}
