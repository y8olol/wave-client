/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import waveclient.waveclient.gui.WidgetScreen;
import waveclient.waveclient.settings.BoolSetting;
import waveclient.waveclient.settings.IntSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.utils.Utils;
import waveclient.waveclient.events.world.TickEvent;

public class DiscordPresence extends Module {
    private static final long APP_ID = 1483294839763239032L;
    private static final String DISCORD_INVITE = "https://discord.gg/RPD4gH34YD";

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> showServer = sgGeneral.add(new BoolSetting.Builder()
        .name("show-server")
        .description("Show the current server/world in the Discord details line.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> showScreenState = sgGeneral.add(new BoolSetting.Builder()
        .name("show-screen-state")
        .description("Show current UI context in Discord presence.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> showInviteButton = sgGeneral.add(new BoolSetting.Builder()
        .name("show-invite-button")
        .description("Adds a Discord invite button to rich presence.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> updateDelay = sgGeneral.add(new IntSetting.Builder()
        .name("update-delay")
        .description("Ticks between rich presence updates.")
        .defaultValue(40)
        .range(10, 200)
        .sliderRange(10, 100)
        .build()
    );

    private long startTimestamp;
    private int timer;

    public DiscordPresence() {
        super(Categories.Misc, "discord-presence", "Displays Wave as your presence on Discord.");
    }

    @Override
    public void onActivate() {
        startTimestamp = System.currentTimeMillis() / 1000L;
        timer = 0;

        boolean connected = DiscordIPC.start(APP_ID, this::updatePresence);
        if (!connected) {
            warning("Could not connect to Discord. Is Discord open?");
            return;
        }

        updatePresence();
    }

    @Override
    public void onDeactivate() {
        DiscordIPC.stop();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!DiscordIPC.isConnected()) return;

        if (timer-- <= 0) {
            updatePresence();
            timer = updateDelay.get();
        }
    }

    private void updatePresence() {
        if (!DiscordIPC.isConnected()) return;

        RichPresence rpc = showInviteButton.get() ? new WaveRichPresence() : new RichPresence();
        rpc.setStart(startTimestamp);

        if (showServer.get()) rpc.setDetails("On " + Utils.getWorldName());
        else rpc.setDetails("Playing Minecraft");

        if (showScreenState.get()) rpc.setState(getStateText());
        if (showServer.get()) rpc.setLargeImage("wave", Utils.getWorldName());
        else rpc.setLargeImage("wave", "Wave Client");

        DiscordIPC.setActivity(rpc);
    }

    private static class WaveRichPresence extends RichPresence {
        @Override
        public JsonObject toJson() {
            JsonObject json = super.toJson();

            JsonArray buttons = new JsonArray();
            JsonObject inviteButton = new JsonObject();
            inviteButton.addProperty("label", "Join Discord");
            inviteButton.addProperty("url", DISCORD_INVITE);
            buttons.add(inviteButton);
            json.add("buttons", buttons);

            // Compatibility fields for rpc wrappers that still map button1_*
            json.addProperty("button1_label", "Join Discord");
            json.addProperty("button1_url", DISCORD_INVITE);

            // Some clients expect button urls in metadata.
            JsonObject metadata = new JsonObject();
            JsonArray urls = new JsonArray();
            urls.add(DISCORD_INVITE);
            metadata.add("button_urls", urls);
            json.add("metadata", metadata);

            return json;
        }
    }

    private String getStateText() {
        if (mc.currentScreen instanceof TitleScreen) return "At title screen";
        if (mc.currentScreen instanceof GameMenuScreen) return "Paused";
        if (mc.currentScreen instanceof ChatScreen) return "Chatting";
        if (mc.currentScreen instanceof WidgetScreen) return "Browsing modules";
        if (mc.world != null) return "Playing";
        return "Idle";
    }
}
