/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.events.packets;

import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;

public class ContainerSlotUpdateEvent {
    private static final ContainerSlotUpdateEvent INSTANCE = new ContainerSlotUpdateEvent();

    public ScreenHandlerSlotUpdateS2CPacket packet;

    public static ContainerSlotUpdateEvent get(ScreenHandlerSlotUpdateS2CPacket packet) {
        INSTANCE.packet = packet;
        return INSTANCE;
    }
}
