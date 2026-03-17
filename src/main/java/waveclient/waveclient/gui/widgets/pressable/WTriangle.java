/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.widgets.pressable;

public abstract class WTriangle extends WPressable {
    public double rotation;

    @Override
    protected void onCalculateSize() {
        double s = theme.textHeight();

        width = s;
        height = s;
    }
}
