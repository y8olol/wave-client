/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets.pressable;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.renderer.packer.GuiTexture;
import waveclient.waveclient.gui.themes.wave.WaveGuiTheme;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.pressable.WButton;
import waveclient.waveclient.gui.widgets.pressable.WConfirmedButton;
import waveclient.waveclient.utils.render.color.Color;

public class WWaveConfirmedButton extends WConfirmedButton implements WaveWidget {
    public WWaveConfirmedButton(String text, String confirmText, GuiTexture texture) {
        super(text, confirmText, texture);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        WaveGuiTheme theme = theme();
        double pad = pad();

        Color outline = theme.outlineColor.get(pressed, mouseOver);
        Color fg = pressedOnce ? theme.backgroundColor.get(pressed, mouseOver) : theme.textColor.get();
        Color bg = pressedOnce ? theme.textColor.get() : theme.backgroundColor.get(pressed, mouseOver);

        renderBackground(renderer, this, outline, bg);

        String text = getText();

        if (text != null) {
            renderer.text(text, x + width / 2 - textWidth / 2, y + pad, fg, false);
        }
        else {
            double ts = theme.textHeight();
            renderer.quad(x + width / 2 - ts / 2, y + pad, ts, ts, texture, fg);
        }
    }
}
