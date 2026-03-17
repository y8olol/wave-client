/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.world;

import waveclient.waveclient.events.packets.PacketEvent;
import waveclient.waveclient.mixininterface.IPlayerInteractEntityC2SPacket;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class MountBypass extends Module {
    private boolean dontCancel;

    public MountBypass() {
        super(Categories.World, "mount-bypass", "Allows you to bypass the IllegalStacks plugin and put chests on entities.");
    }

    @EventHandler
    public void onSendPacket(PacketEvent.Send event) {
        if (dontCancel) {
            dontCancel = false;
            return;
        }

        if (event.packet instanceof IPlayerInteractEntityC2SPacket packet) {
            if (packet.wave$getType() == PlayerInteractEntityC2SPacket.InteractType.INTERACT_AT && packet.wave$getEntity() instanceof AbstractDonkeyEntity) event.cancel();
        }
    }
}
