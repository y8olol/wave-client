/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.player;

import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import waveclient.waveclient.mixin.ClientPlayNetworkHandlerMixin;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @see ClientPlayNetworkHandlerMixin#onPlayerPositionLookHead(PlayerPositionLookS2CPacket, CallbackInfo, LocalFloatRef, LocalFloatRef)
 */
public class NoRotate extends Module {
    public NoRotate() {
        super(Categories.Player, "no-rotate", "Attempts to block rotations sent from server to client.");
    }
}
