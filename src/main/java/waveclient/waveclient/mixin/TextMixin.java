/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import waveclient.waveclient.mixininterface.IText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Text.class)
public interface TextMixin extends IText {
    @Override
    default void wave$invalidateCache() {}
}
