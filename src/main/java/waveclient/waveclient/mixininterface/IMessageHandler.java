/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;

public interface IMessageHandler {
    /** Only valid inside of {@link net.minecraft.client.gui.hud.ChatHud#addMessage(Text, MessageSignatureData, MessageIndicator)} call */
    GameProfile wave$getSender();
}
