/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.render;

import waveclient.waveclient.events.Cancellable;
import net.minecraft.client.render.model.json.Transformation;

public class ApplyTransformationEvent extends Cancellable {
    private static final ApplyTransformationEvent INSTANCE = new ApplyTransformationEvent();

    public Transformation transformation;
    public boolean leftHanded;

    public static ApplyTransformationEvent get(Transformation transformation, boolean leftHanded) {
        INSTANCE.setCancelled(false);

        INSTANCE.transformation = transformation;
        INSTANCE.leftHanded = leftHanded;

        return INSTANCE;
    }
}
