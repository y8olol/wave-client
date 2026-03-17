/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import net.minecraft.item.ItemStack;

public interface IAbstractFurnaceScreenHandler {
    boolean wave$isItemSmeltable(ItemStack itemStack);
}
