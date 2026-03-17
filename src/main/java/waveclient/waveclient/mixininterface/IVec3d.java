/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector3d;

@SuppressWarnings("UnusedReturnValue")
public interface IVec3d {
    Vec3d wave$set(double x, double y, double z);

    default Vec3d wave$set(Vec3i vec) {
        return wave$set(vec.getX(), vec.getY(), vec.getZ());
    }

    default Vec3d wave$set(Vector3d vec) {
        return wave$set(vec.x, vec.y, vec.z);
    }

    default Vec3d wave$set(Vec3d pos) {
        return wave$set(pos.x, pos.y, pos.z);
    }

    Vec3d wave$setXZ(double x, double z);

    Vec3d wave$setY(double y);
}
