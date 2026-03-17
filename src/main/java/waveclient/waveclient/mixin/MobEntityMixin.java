/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.movement.EntityControl;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @ModifyReturnValue(method = "hasSaddleEquipped", at = @At("RETURN"))
    private boolean hasSaddleEquipped(boolean original) {
        return Modules.get().get(EntityControl.class).spoofSaddle() || original;
    }
}
