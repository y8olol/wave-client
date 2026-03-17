/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import waveclient.waveclient.WaveClient;
import waveclient.waveclient.commands.Commands;
import waveclient.waveclient.systems.config.Config;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.movement.GUIMove;
import waveclient.waveclient.systems.modules.render.NoRender;
import waveclient.waveclient.utils.Utils;
import waveclient.waveclient.utils.misc.text.WaveClickEvent;
import waveclient.waveclient.utils.misc.text.RunnableClickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.ClickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.client.util.InputUtil.*;

@Mixin(value = Screen.class, priority = 500) // needs to be before baritone
public abstract class ScreenMixin {
    @Inject(method = "renderInGameBackground", at = @At("HEAD"), cancellable = true)
    private void onRenderInGameBackground(CallbackInfo info) {
        if (Utils.canUpdate() && Modules.get().get(NoRender.class).noGuiBackground())
            info.cancel();
    }

    @Inject(method = "handleBasicClickEvent", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", remap = false), cancellable = true)
    private static void onHandleBasicClickEvent(ClickEvent clickEvent, MinecraftClient client, Screen screen, CallbackInfo ci) {
        if (clickEvent instanceof RunnableClickEvent runnableClickEvent) {
            runnableClickEvent.runnable.run();
            ci.cancel();
        }
        else if (clickEvent instanceof WaveClickEvent meteorClickEvent && meteorClickEvent.value.startsWith(Config.get().prefix.get())) {
            try {
                Commands.dispatch(meteorClickEvent.value.substring(Config.get().prefix.get().length()));
            } catch (CommandSyntaxException e) {
                WaveClient.LOG.error("Failed to run command", e);
            } finally {
                ci.cancel();
            }
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) (this) instanceof ChatScreen) return;
        GUIMove guiMove = Modules.get().get(GUIMove.class);
        List<Integer> arrows = List.of(GLFW_KEY_RIGHT, GLFW_KEY_LEFT, GLFW_KEY_DOWN,  GLFW_KEY_UP);
        if ((guiMove.disableArrows() && arrows.contains(input.key())) || (guiMove.disableSpace() && input.key() == GLFW_KEY_SPACE)) {
            cir.setReturnValue(true);
        }
    }
}
