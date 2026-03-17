/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.misc;

import waveclient.waveclient.events.entity.EntityAddedEvent;
import waveclient.waveclient.settings.BoolSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.settings.StringSetting;
import waveclient.waveclient.systems.friends.Friends;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;

public class MessageAura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> message = sgGeneral.add(new StringSetting.Builder()
        .name("message")
        .description("The specified message sent to the player.")
        .defaultValue("Wave rides high!")
        .build()
    );

    private final Setting<Boolean> ignoreFriends = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-friends")
        .description("Will not send any messages to people friended.")
        .defaultValue(false)
        .build()
    );

    public MessageAura() {
        super(Categories.Misc, "message-aura", "Sends a specified message to any player that enters render distance.");
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent event) {
        if (!(event.entity instanceof PlayerEntity) || event.entity.getUuid().equals(mc.player.getUuid())) return;

        if (!ignoreFriends.get() || (ignoreFriends.get() && !Friends.get().isFriend((PlayerEntity)event.entity))) {
            ChatUtils.sendPlayerMsg("/msg " + event.entity.getName().getString() + " " + message.get());
        }
    }
}
