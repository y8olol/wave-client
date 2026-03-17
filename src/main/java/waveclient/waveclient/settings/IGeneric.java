/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.settings;

import waveclient.waveclient.gui.GuiTheme;
import waveclient.waveclient.gui.WidgetScreen;
import waveclient.waveclient.utils.misc.ICopyable;
import waveclient.waveclient.utils.misc.ISerializable;

public interface IGeneric<T extends IGeneric<T>> extends ICopyable<T>, ISerializable<T> {
    WidgetScreen createScreen(GuiTheme theme, GenericSetting<T> setting);
}
