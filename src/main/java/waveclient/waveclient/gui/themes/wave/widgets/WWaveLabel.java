/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.WLabel;

public class WWaveLabel extends WLabel implements WaveWidget {
    public WWaveLabel(String text, boolean title) {
        super(text, title);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!text.isEmpty()) {
            renderer.text(text, x, y, color != null ? color : (title ? theme().titleTextColor.get() : theme().textColor.get()), title);
        }
    }
}
