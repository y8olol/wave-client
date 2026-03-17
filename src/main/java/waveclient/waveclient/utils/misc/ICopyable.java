/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.utils.misc;

public interface ICopyable<T extends ICopyable<T>> {
    T set(T value);

    T copy();
}
