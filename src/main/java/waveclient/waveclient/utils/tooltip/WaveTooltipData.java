/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.utils.tooltip;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;

public interface WaveTooltipData extends TooltipData {
    TooltipComponent getComponent();
}
