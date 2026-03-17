/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.tabs;

import waveclient.waveclient.gui.GuiTheme;
import net.minecraft.client.gui.screen.Screen;

import static waveclient.waveclient.WaveClient.mc;

public abstract class Tab {
    public final String name;

    public Tab(String name) {
        this.name = name;
    }

    public void openScreen(GuiTheme theme) {
        TabScreen screen = this.createScreen(theme);
        screen.addDirect(theme.topBar()).top().centerX();
        mc.setScreen(screen);
    }

    public abstract TabScreen createScreen(GuiTheme theme);

    public abstract boolean isScreen(Screen screen);
}
