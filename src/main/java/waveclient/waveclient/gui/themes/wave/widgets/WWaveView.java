/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.themes.wave.widgets;

import waveclient.waveclient.gui.renderer.GuiRenderer;
import waveclient.waveclient.gui.themes.wave.WaveWidget;
import waveclient.waveclient.gui.widgets.containers.WView;

public class WWaveView extends WView implements WaveWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (canScroll && hasScrollBar) {
            renderer.quad(handleX(), handleY(), handleWidth(), handleHeight(), theme().scrollbarColor.get(focused, handleMouseOver));
        }
    }
}
