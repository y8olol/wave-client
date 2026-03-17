/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveGuiTheme;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.WVerticalSeparator;
import waveclient.waveclient.utils.render.color.Color;

public class WWaveVerticalSeparator extends WVerticalSeparator implements WaveWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        WaveGuiTheme theme = theme();
        Color colorEdges = theme.separatorEdges.get();
        Color colorCenter = theme.separatorCenter.get();

        double s = theme.scale(1);
        double offsetX = Math.round(width / 2.0);

        renderer.quad(x + offsetX, y, s, height / 2, colorEdges, colorEdges, colorCenter, colorCenter);
        renderer.quad(x + offsetX, y + height / 2, s, height / 2, colorCenter, colorCenter, colorEdges, colorEdges);
    }
}
