/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.renderer;

import waveclient.waveclient.WaveClient;
import waveclient.waveclient.events.wave.CustomFontChangedEvent;
import waveclient.waveclient.gui.WidgetScreen;
import waveclient.waveclient.renderer.text.CustomTextRenderer;
import waveclient.waveclient.renderer.text.FontFace;
import waveclient.waveclient.renderer.text.FontFamily;
import waveclient.waveclient.renderer.text.FontInfo;
import waveclient.waveclient.systems.config.Config;
import waveclient.waveclient.utils.PreInit;
import waveclient.waveclient.utils.render.FontUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static waveclient.waveclient.WaveClient.mc;

public class Fonts {
    public static final String[] BUILTIN_FONTS = { "JetBrains Mono", "Comfortaa", "Tw Cen MT", "Pixelation" };

    public static String DEFAULT_FONT_FAMILY;
    public static FontFace DEFAULT_FONT;

    public static final List<FontFamily> FONT_FAMILIES = new ArrayList<>();
    public static CustomTextRenderer RENDERER;

    private Fonts() {
    }

    @PreInit
    public static void refresh() {
        FONT_FAMILIES.clear();

        for (String builtinFont : BUILTIN_FONTS) {
            FontUtils.loadBuiltin(FONT_FAMILIES, builtinFont);
        }

        for (String fontPath : FontUtils.getSearchPaths()) {
            FontUtils.loadSystem(FONT_FAMILIES, new File(fontPath));
        }

        FONT_FAMILIES.sort(Comparator.comparing(FontFamily::getName));

        WaveClient.LOG.info("Found {} font families.", FONT_FAMILIES.size());

        DEFAULT_FONT_FAMILY = pickDefaultFamily();
        DEFAULT_FONT = getFamily(DEFAULT_FONT_FAMILY).get(FontInfo.Type.Regular);

        Config config = Config.get();
        if (config != null && config.customFont.get() && "Comfortaa".equalsIgnoreCase(config.font.get().info.family())) {
            config.font.set(DEFAULT_FONT);
        }

        load(config != null ? config.font.get() : DEFAULT_FONT);
    }

    private static String pickDefaultFamily() {
        String[] preferred = { "Bahnschrift", "Segoe UI", "Trebuchet MS", "Tw Cen MT" };

        for (String family : preferred) {
            FontFamily found = getFamily(family);
            if (found != null && found.hasType(FontInfo.Type.Regular)) return found.getName();
        }

        return FontUtils.getBuiltinFontInfo(BUILTIN_FONTS[2]).family();
    }

    public static void load(FontFace fontFace) {
        if (RENDERER != null) {
            if (RENDERER.fontFace.equals(fontFace)) return;
            else RENDERER.destroy();
        }

        try {
            RENDERER = new CustomTextRenderer(fontFace);
            WaveClient.EVENT_BUS.post(CustomFontChangedEvent.get());
        }
        catch (Exception e) {
            if (fontFace.equals(DEFAULT_FONT)) {
                throw new RuntimeException("Failed to load default font: " + fontFace, e);
            }

            WaveClient.LOG.error("Failed to load font: {}", fontFace, e);
            load(Fonts.DEFAULT_FONT);
        }

        if (mc.currentScreen instanceof WidgetScreen && Config.get().customFont.get()) {
            ((WidgetScreen) mc.currentScreen).invalidate();
        }
    }

    public static FontFamily getFamily(String name) {
        for (FontFamily fontFamily : Fonts.FONT_FAMILIES) {
            if (fontFamily.getName().equalsIgnoreCase(name)) {
                return fontFamily;
            }
        }

        return null;
    }
}
