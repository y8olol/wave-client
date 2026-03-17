/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.tabs.Tab;
import waveclient.waveclient.gui.tabs.TabScreen;
import waveclient.waveclient.gui.tabs.Tabs;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.WTopBar;
import waveclient.waveclient.gui.widgets.pressable.WPressable;
import waveclient.waveclient.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;

import static waveclient.waveclient.WaveClient.mc;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class WWaveTopBar extends WTopBar implements WaveWidget {
    public WWaveTopBar() {
        spacing = 2;
    }

    @Override
    public void init() {
        for (Tab tab : Tabs.get()) {
            add(new WWaveTopBarButton(tab));
        }
    }

    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        if (pressed) return theme().accentColor.get();
        if (hovered) return theme().backgroundColor.get(false, true);

        Color idle = new Color(theme().backgroundColor.get(false, false));
        idle.a = 215;
        return idle;
    }

    @Override
    protected Color getNameColor() {
        return theme().titleTextColor.get();
    }

    private class WWaveTopBarButton extends WPressable {
        private final Tab tab;
        private double textWidth;
        private double hoverProgress;
        private double activeProgress;

        private WWaveTopBarButton(Tab tab) {
            this.tab = tab;
        }

        @Override
        protected void onCalculateSize() {
            double pad = pad();
            textWidth = theme.textWidth(tab.name);

            width = pad + textWidth + pad + theme.scale(6);
            height = pad + theme.textHeight() + pad;
        }

        @Override
        protected void onPressed(int button) {
            Screen screen = mc.currentScreen;

            if (!(screen instanceof TabScreen) || ((TabScreen) screen).tab != tab) {
                double mouseX = mc.mouse.getX();
                double mouseY = mc.mouse.getY();

                tab.openScreen(theme);
                glfwSetCursorPos(mc.getWindow().getHandle(), mouseX, mouseY);
            }
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            boolean active = mc.currentScreen instanceof TabScreen && ((TabScreen) mc.currentScreen).tab == tab;
            var waveTheme = WWaveTopBar.this.theme();

            hoverProgress += delta * 10 * (mouseOver ? 1 : -1);
            hoverProgress = Math.max(0, Math.min(1, hoverProgress));

            activeProgress += delta * 10 * (active ? 1 : -1);
            activeProgress = Math.max(0, Math.min(1, activeProgress));

            Color base = new Color(waveTheme.backgroundColor.get(false, false));
            Color hover = new Color(waveTheme.backgroundColor.get(false, true));
            Color accent = new Color(waveTheme.accentColor.get());

            Color body = mix(base, hover, hoverProgress * 0.8);
            body = mix(body, accent, activeProgress * 0.33);
            renderer.quad(x, y, width, height, body);

            double cut = waveTheme.scale(6);
            renderer.triangle(x + width - cut, y, x + width, y, x + width, y + cut, waveTheme.backgroundColor.get(false, false));

            if (activeProgress > 0) {
                Color line = mix(accent, body, 0.35);
                renderer.quad(x, y + height - waveTheme.scale(2), width - cut / 2, waveTheme.scale(2), line);
            }

            Color textColor = mix(waveTheme.textSecondaryColor.get(), waveTheme.titleTextColor.get(), Math.max(hoverProgress * 0.6, activeProgress));
            renderer.text(tab.name, x + pad(), y + pad(), textColor, false);
        }

        private Color mix(Color a, Color b, double t) {
            if (t <= 0) return new Color(a);
            if (t >= 1) return new Color(b);

            return new Color(
                (int) Math.round(a.r + (b.r - a.r) * t),
                (int) Math.round(a.g + (b.g - a.g) * t),
                (int) Math.round(a.b + (b.b - a.b) * t),
                (int) Math.round(a.a + (b.a - a.a) * t)
            );
        }
    }
}
