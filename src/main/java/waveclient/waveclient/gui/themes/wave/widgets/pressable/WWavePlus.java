/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets.pressable;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveGuiTheme;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.pressable.WPlus;

public class WWavePlus extends WPlus implements WaveWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        WaveGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme.plusColor.get());
        renderer.quad(x + width / 2 - s / 2, y + pad, s, height - pad * 2, theme.plusColor.get());
    }
}
