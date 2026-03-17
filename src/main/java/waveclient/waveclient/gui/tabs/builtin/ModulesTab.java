/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.tabs.builtin;

import waveclient.waveclient.gui.GuiTheme;
import waveclient.waveclient.gui.GuiThemes;
import waveclient.waveclient.gui.tabs.Tab;
import waveclient.waveclient.gui.tabs.TabScreen;
import net.minecraft.client.gui.screen.Screen;

public class ModulesTab extends Tab {
    public ModulesTab() {
        super("Modules");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return theme.modulesScreen();
    }

    @Override
    public boolean isScreen(Screen screen) {
        return GuiThemes.get().isModulesScreen(screen);
    }
}
