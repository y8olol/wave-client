/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

public interface IRenderPipeline {
    void wave$setLineSmooth(boolean lineSmooth);

    boolean wave$getLineSmooth();
}
