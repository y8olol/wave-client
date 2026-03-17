/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import waveclient.waveclient.commands.Command;
import waveclient.waveclient.renderer.Fonts;
import waveclient.waveclient.systems.Systems;
import waveclient.waveclient.systems.friends.Friend;
import waveclient.waveclient.systems.friends.Friends;
import waveclient.waveclient.utils.network.Capes;
import waveclient.waveclient.utils.network.WaveExecutor;
import net.minecraft.command.CommandSource;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "Reloads many systems.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            warning("Reloading systems, this may take a while.");

            Systems.load();
            Capes.init();
            Fonts.refresh();
            WaveExecutor.execute(() -> Friends.get().forEach(Friend::updateInfo));

            return SINGLE_SUCCESS;
        });
    }
}
