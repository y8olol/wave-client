/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.utils.entity.simulator;

import net.minecraft.util.hit.HitResult;

public class SimulationStep {
    public static final SimulationStep MISS = new SimulationStep(true);

    public boolean shouldStop;
    public HitResult[] hitResults;

    public SimulationStep(boolean stop, HitResult... hitResults) {
        this.shouldStop = stop;
        this.hitResults = hitResults;
    }
}
