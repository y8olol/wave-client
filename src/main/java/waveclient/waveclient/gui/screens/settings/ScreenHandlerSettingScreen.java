/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.screens.settings;

import waveclient.waveclient.gui.GuiTheme;
import waveclient.waveclient.gui.screens.settings.base.CollectionListSettingScreen;
import waveclient.waveclient.gui.widgets.WWidget;
import waveclient.waveclient.settings.Setting;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;

import java.util.List;

public class ScreenHandlerSettingScreen extends CollectionListSettingScreen<ScreenHandlerType<?>> {
    public ScreenHandlerSettingScreen(GuiTheme theme, Setting<List<ScreenHandlerType<?>>> setting) {
        super(theme, "Select Screen Handlers", setting, setting.get(), Registries.SCREEN_HANDLER);
    }

    @Override
    protected WWidget getValueWidget(ScreenHandlerType<?> value) {
        return theme.label(getName(value));
    }

    @Override
    protected String[] getValueNames(ScreenHandlerType<?> type) {
        return new String[]{
            getName(type)
        };
    }

    private static String getName(ScreenHandlerType<?> type) {
        return Registries.SCREEN_HANDLER.getId(type).toString();
    }
}
