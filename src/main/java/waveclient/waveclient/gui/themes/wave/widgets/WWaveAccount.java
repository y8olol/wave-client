/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.WidgetScreen;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.WAccount;
import waveclient.waveclient.systems.accounts.Account;
import waveclient.waveclient.utils.render.color.Color;

public class WWaveAccount extends WAccount implements WaveWidget {
    public WWaveAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}
