/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.render.BetterTooltips;
import net.minecraft.item.ItemGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemGroups.class)
public abstract class ItemGroupsMixin {
    @ModifyReturnValue(method = "updateDisplayContext", at = @At("RETURN"))
    private static boolean modifyReturn(boolean original) {
        return original || Modules.get().get(BetterTooltips.class).updateTooltips();
    }
}
