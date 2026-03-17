/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import waveclient.waveclient.WaveClient;
import waveclient.waveclient.commands.Command;
import waveclient.waveclient.commands.arguments.PlayerArgumentType;
import waveclient.waveclient.events.wave.KeyEvent;
import waveclient.waveclient.events.wave.MouseClickEvent;
import waveclient.waveclient.utils.misc.input.Input;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

public class SpectateCommand extends Command {

    private final StaticListener shiftListener = new StaticListener();

    public SpectateCommand() {
        super("spectate", "Allows you to spectate nearby players");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("reset").executes(context -> {
            mc.setCameraEntity(mc.player);
            return SINGLE_SUCCESS;
        }));

        builder.then(argument("player", PlayerArgumentType.create()).executes(context -> {
            mc.setCameraEntity(PlayerArgumentType.get(context));
            mc.player.sendMessage(Text.literal("Sneak to un-spectate."), true);
            WaveClient.EVENT_BUS.subscribe(shiftListener);
            return SINGLE_SUCCESS;
        }));
    }

    private static class StaticListener {
        @EventHandler
        private void onKey(KeyEvent event) {
            if (Input.isPressed(mc.options.sneakKey)) {
                mc.setCameraEntity(mc.player);
                event.cancel();
                WaveClient.EVENT_BUS.unsubscribe(this);
            }
        }

        @EventHandler
        private void onMouse(MouseClickEvent event) {
            if (Input.isPressed(mc.options.sneakKey)) {
                mc.setCameraEntity(mc.player);
                event.cancel();
                WaveClient.EVENT_BUS.unsubscribe(this);
            }
        }
    }
}
