/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import waveclient.waveclient.mixininterface.IText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Language;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MutableText.class)
public abstract class MutableTextMixin implements IText {
    @Shadow
    private @Nullable Language language;

    @Override
    public void wave$invalidateCache() {
        this.language = null;
    }
}
