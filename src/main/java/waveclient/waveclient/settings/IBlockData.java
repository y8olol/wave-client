/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.settings;

import waveclient.waveclient.gui.GuiTheme;
import waveclient.waveclient.gui.WidgetScreen;
import waveclient.waveclient.utils.misc.IChangeable;
import waveclient.waveclient.utils.misc.ICopyable;
import waveclient.waveclient.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
