/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

public interface IChatHudLineVisible extends IChatHudLine {
    boolean wave$isStartOfEntry();
    void wave$setStartOfEntry(boolean start);
}
