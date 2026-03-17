/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.WMultiLabel;
import waveclient.waveclient.utils.render.color.Color;

public class WWaveMultiLabel extends WMultiLabel implements WaveWidget {
    public WWaveMultiLabel(String text, boolean title, double maxWidth) {
        super(text, title, maxWidth);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double h = theme.textHeight(title);
        Color defaultColor = theme().textColor.get();

        for (int i = 0; i < lines.size(); i++) {
            renderer.text(lines.get(i), x, y + h * i, color != null ? color : defaultColor, false);
        }
    }
}
