/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.utils.BaseWidget;
import waveclient.waveclient.gui.widgets.WWidget;
import waveclient.waveclient.utils.render.color.Color;

public interface WaveWidget extends BaseWidget {
    default WaveGuiTheme theme() {
        return (WaveGuiTheme) getTheme();
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, Color outlineColor, Color backgroundColor) {
        WaveGuiTheme theme = theme();
        double border = theme.scale(1);
        double accent = theme.scale(2);

        renderer.quad(widget.x, widget.y, widget.width, widget.height, backgroundColor);

        Color topGlow = new Color(outlineColor);
        topGlow.a = Math.min(200, topGlow.a + 40);
        renderer.quad(widget.x, widget.y, widget.width, border, topGlow);

        Color bottomBorder = new Color(outlineColor);
        bottomBorder.a = Math.max(110, bottomBorder.a);
        renderer.quad(widget.x, widget.y + widget.height - border, widget.width, border, bottomBorder);

        Color sideAccent = new Color(outlineColor);
        sideAccent.a = Math.max(120, sideAccent.a);
        renderer.quad(widget.x, widget.y + border, accent, widget.height - border * 2, sideAccent);
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, boolean pressed, boolean mouseOver) {
        WaveGuiTheme theme = theme();
        renderBackground(renderer, widget, theme.outlineColor.get(pressed, mouseOver), theme.backgroundColor.get(pressed, mouseOver));
    }
}
