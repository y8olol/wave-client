/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.movement.Jesus;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static waveclient.waveclient.WaveClient.mc;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @ModifyReturnValue(method = "canWalkOnPowderSnow", at = @At("RETURN"))
    private static boolean onCanWalkOnPowderSnow(boolean original, Entity entity) {
        if (entity == mc.player && Modules.get().get(Jesus.class).canWalkOnPowderSnow()) return true;
        return original;
    }
}
