/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.render.NoRender;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.MobSpawnerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.MobSpawnerBlockEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerBlockEntityRenderer.class)
public abstract class MobSpawnerBlockEntityRendererMixin implements BlockEntityRenderer<MobSpawnerBlockEntity, MobSpawnerBlockEntityRenderState> {
    @Inject(method = "renderDisplayEntity", at = @At("HEAD"), cancellable = true)
    private static void onRenderDisplayEntity(CallbackInfo ci) {
        if (Modules.get().get(NoRender.class).noMobInSpawner()) ci.cancel();
    }
}
