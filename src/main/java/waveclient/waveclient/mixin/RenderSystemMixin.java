/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import waveclient.waveclient.renderer.MeshUniforms;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.misc.InventoryTweaks;
import waveclient.waveclient.utils.render.postprocess.ChamsShader;
import waveclient.waveclient.utils.render.postprocess.OutlineUniforms;
import waveclient.waveclient.utils.render.postprocess.PostProcessShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static waveclient.waveclient.WaveClient.mc;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(method = "flipFrame", at = @At("TAIL"))
    private static void wave$flipFrame(CallbackInfo info) {
        MeshUniforms.flipFrame();
        PostProcessShader.flipFrame();
        ChamsShader.flipFrame();
        OutlineUniforms.flipFrame();

        if (Modules.get() == null || mc.player == null) return;
        if (Modules.get().get(InventoryTweaks.class).frameInput()) ((MinecraftClientAccessor) mc).wave$handleInputEvents();
    }
}
