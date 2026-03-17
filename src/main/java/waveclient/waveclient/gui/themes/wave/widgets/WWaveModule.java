/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveGuiTheme;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.utils.AlignmentX;
import waveclient.waveclient.gui.widgets.pressable.WPressable;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.utils.render.color.Color;
import net.minecraft.util.math.MathHelper;

import static waveclient.waveclient.WaveClient.mc;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class WWaveModule extends WPressable implements WaveWidget {
    private final Module module;
    private final String title;
    private static final String SETTINGS_HINT = ">";

    private double titleWidth;
    private double settingsHintWidth;

    private double hoverProgress;
    private double activeProgress;

    public WWaveModule(Module module, String title) {
        this.module = module;
        this.title = title;
        this.tooltip = module.description;

        if (module.isActive()) {
            activeProgress = 1;
        } else {
            activeProgress = 0;
        }
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
    public double pad() {
        return theme.scale(4);
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();

        if (titleWidth == 0) titleWidth = theme.textWidth(title);
        if (settingsHintWidth == 0) settingsHintWidth = theme.textWidth(SETTINGS_HINT);

        width = pad + titleWidth + pad;
        height = pad + theme.textHeight() + pad;
    }

    @Override
    protected void onPressed(int button) {
        if (button == GLFW_MOUSE_BUTTON_LEFT) module.toggle();
        else if (button == GLFW_MOUSE_BUTTON_RIGHT) mc.setScreen(theme.moduleScreen(module));
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        WaveGuiTheme theme = theme();
        double pad = pad();

        hoverProgress += delta * 9 * (mouseOver ? 1 : -1);
        hoverProgress = MathHelper.clamp(hoverProgress, 0, 1);

        activeProgress += delta * 7 * (module.isActive() ? 1 : -1);
        activeProgress = MathHelper.clamp(activeProgress, 0, 1);

        Color base = new Color(theme.backgroundColor.get(false, false));
        Color activeBase = mix(base, theme.accentColor.get(), 0.28);
        Color hover = mix(base, theme.moduleBackground.get(), hoverProgress * 0.75);
        hover = mix(hover, activeBase, activeProgress * 0.85);
        Color right = mix(hover, theme.backgroundColor.get(false, true), 0.45);
        renderer.quad(x, y, width, height, hover, right);

        double cut = theme.scale(5);
        renderer.triangle(x + width - cut, y, x + width, y, x + width, y + cut, base);

        Color topLine = mix(theme.accentColor.get(), hover, 0.75);
        topLine.a = 95;
        renderer.quad(x, y, width, theme.scale(1), topLine);

        if (activeProgress > 0) {
            double stripWidth = theme.scale(3);
            renderer.quad(x, y, stripWidth, height, theme.accentColor.get());

            Color activeGlow = mix(theme.accentColor.get(), base, 0.62);
            activeGlow.a = 165;
            renderer.quad(x + stripWidth, y, Math.max(0, width * (0.2 + activeProgress * 0.5)), height, activeGlow, Color.CLEAR);

            Color activeBottom = mix(theme.accentColor.get(), base, 0.45);
            activeBottom.a = 140;
            renderer.quad(x, y + height - theme.scale(1), width, theme.scale(1), activeBottom);
        }

        double x = this.x + pad;
        double w = width - pad * 2;

        if (mouseOver) w -= settingsHintWidth + pad;

        if (theme.moduleAlignment.get() == AlignmentX.Center) {
            x += w / 2 - titleWidth / 2;
        }
        else if (theme.moduleAlignment.get() == AlignmentX.Right) {
            x += w - titleWidth;
        }

        Color titleColor = mix(theme.textSecondaryColor.get(), theme.textColor.get(), Math.max(hoverProgress * 0.8, activeProgress * 1.15));
        renderer.text(title, x, y + pad, titleColor, false);

        if (mouseOver) {
            renderer.text(SETTINGS_HINT, this.x + width - pad - settingsHintWidth, y + pad, theme.textSecondaryColor.get(), false);
        }
    }
}
