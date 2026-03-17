/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.utils.misc.text;

/**
 * Allows arbitrary code execution in a click event
 */
public class RunnableClickEvent extends WaveClickEvent {
    public final Runnable runnable;

    public RunnableClickEvent(Runnable runnable) {
        super(null); // Should ensure no vanilla code is triggered, and only we handle it
        this.runnable = runnable;
    }
}
