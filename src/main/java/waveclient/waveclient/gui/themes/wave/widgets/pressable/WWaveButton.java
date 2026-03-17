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
import waveclient.waveclient.utils.render.color.Color;

public class WWaveButton extends WButton implements WaveWidget {
    private double hoverProgress;

    public WWaveButton(String text, GuiTexture texture) {
        super(text, texture);
    }

    private static Color mix(Color a, Color b, double t) {
        if (t <= 0) return new Color(a);
        if (t >= 1) return new Color(b);

        return new Color(
            (int) Math.round(a.r + (b.r - a.r) * t),
            (int) Math.round(a.g + (b.g - a.g) * t),
            (int) Math.round(a.b + (b.b - a.b) * t),
            (int) Math.round(a.a + (b.a - a.a) * t)
        );
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        WaveGuiTheme theme = theme();
        double pad = pad();

        hoverProgress += delta * 10 * (mouseOver ? 1 : -1);
        hoverProgress = Math.max(0, Math.min(1, hoverProgress));

        Color outline = mix(theme.outlineColor.get(false, false), theme.outlineColor.get(false, true), hoverProgress);
        Color background = mix(theme.backgroundColor.get(false, false), theme.backgroundColor.get(false, true), hoverProgress);

        if (pressed) {
            outline = new Color(theme.accentColor.get());
            background = mix(background, theme.accentColor.get(), 0.3);
        }

        renderBackground(renderer, this, outline, background);

        if (mouseOver || pressed) {
            Color glow = mix(theme.accentColor.get(), theme.backgroundColor.get(false, false), 0.6);
            glow.a = 100;
            renderer.quad(x, y + height - theme.scale(1), width, theme.scale(1), glow);
        }

        if (text != null) {
            Color textColor = pressed ? theme.titleTextColor.get() : mix(theme.textSecondaryColor.get(), theme.textColor.get(), Math.max(hoverProgress, 0.35));
            renderer.text(text, x + width / 2 - textWidth / 2, y + pad, textColor, false);
        }
        else {
            double ts = theme.textHeight();
            renderer.quad(x + width / 2 - ts / 2, y + pad, ts, ts, texture, theme.textColor.get());
        }
    }
}
