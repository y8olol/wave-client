/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.authlib.GameProfile;
import waveclient.waveclient.mixininterface.IChatHudLine;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ChatHudLine.class)
public abstract class ChatHudLineMixin implements IChatHudLine {
    @Shadow @Final private Text content;
    @Unique private int id;
    @Unique private GameProfile sender;

    @Override
    public String wave$getText() {
        return content.getString();
    }

    @Override
    public int wave$getId() {
        return id;
    }

    @Override
    public void wave$setId(int id) {
        this.id = id;
    }

    @Override
    public GameProfile wave$getSender() {
        return sender;
    }

    @Override
    public void wave$setSender(GameProfile profile) {
        sender = profile;
    }
}
