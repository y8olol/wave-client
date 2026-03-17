/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import waveclient.waveclient.mixininterface.IAbstractFurnaceScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractFurnaceScreenHandler.class)
public abstract class AbstractFurnaceScreenHandlerMixin implements IAbstractFurnaceScreenHandler {
    @Shadow
    protected abstract boolean isSmeltable(ItemStack itemStack);

    @Override
    public boolean wave$isItemSmeltable(ItemStack itemStack) {
        return isSmeltable(itemStack);
    }
}
