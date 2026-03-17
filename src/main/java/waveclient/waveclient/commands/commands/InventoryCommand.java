/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import waveclient.waveclient.commands.Command;
import waveclient.waveclient.commands.arguments.PlayerArgumentType;
import waveclient.waveclient.utils.Utils;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.command.CommandSource;

public class InventoryCommand extends Command {
    public InventoryCommand() {
        super("inventory", "Allows you to see parts of another player's inventory.", "inv", "invsee");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("player", PlayerArgumentType.create()).executes(context -> {
            Utils.screenToOpen = new InventoryScreen(PlayerArgumentType.get(context));
            return SINGLE_SUCCESS;
        }));
    }
}
