/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.authlib.GameProfile;
import waveclient.waveclient.mixininterface.IChatHudLineVisible;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChatHudLine.Visible.class)
public abstract class ChatHudLineVisibleMixin implements IChatHudLineVisible {
    @Shadow @Final private OrderedText content;
    @Unique private int id;
    @Unique private GameProfile sender;
    @Unique private boolean startOfEntry;

    @Override
    public String wave$getText() {
        StringBuilder sb = new StringBuilder();

        content.accept((index, style, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });

        return sb.toString();
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

    @Override
    public boolean wave$isStartOfEntry() {
        return startOfEntry;
    }

    @Override
    public void wave$setStartOfEntry(boolean start) {
        startOfEntry = start;
    }
}
