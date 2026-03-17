/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.WWidget;
import waveclient.waveclient.gui.widgets.containers.WWindow;
import waveclient.waveclient.utils.render.color.SettingColor;

public class WWaveWindow extends WWindow implements WaveWidget {
    public WWaveWindow(WWidget icon, String title) {
        super(icon, title);
    }

    @Override
    protected WHeader header(WWidget icon) {
        return new WWaveHeader(icon);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (expanded || animProgress > 0) {
            renderer.quad(x, y + header.height, width, height - header.height, theme().backgroundColor.get(false, false), theme().backgroundColor.get(false, true));

            var accent = theme().accentColor.get();
            renderer.quad(x, y + header.height, width, theme().scale(1), new SettingColor(accent.r, accent.g, accent.b, 80));
        }
    }

    private class WWaveHeader extends WHeader {
        public WWaveHeader(WWidget icon) {
            super(icon);
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            SettingColor accent = theme().accentColor.get();
            renderer.quad(x, y, width, height, new SettingColor(12, 24, 34, 235), new SettingColor(22, 46, 64, 245));
            renderer.quad(x, y + height - theme().scale(1), width, theme().scale(1), accent);
        }
    }
}
