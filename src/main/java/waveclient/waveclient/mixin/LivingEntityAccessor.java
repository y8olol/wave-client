/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker("swimUpward")
    void wave$swimUpwards(TagKey<Fluid> fluid);

    @Accessor("jumping")
    boolean wave$isJumping();

    @Accessor("jumpingCooldown")
    int wave$getJumpCooldown();

    @Accessor("jumpingCooldown")
    void wave$setJumpCooldown(int cooldown);
}
