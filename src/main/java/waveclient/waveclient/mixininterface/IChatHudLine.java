/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixininterface;

import com.mojang.authlib.GameProfile;

public interface IChatHudLine {
    String wave$getText();

    int wave$getId();

    void wave$setId(int id);

    GameProfile wave$getSender();

    void wave$setSender(GameProfile profile);
}
