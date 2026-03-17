/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets.pressable;

import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.pressable.WFavorite;
import waveclient.waveclient.utils.render.color.Color;

public class WWaveFavorite extends WFavorite implements WaveWidget {
    public WWaveFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
