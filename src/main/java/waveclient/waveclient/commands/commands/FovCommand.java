/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import waveclient.waveclient.commands.Command;
import waveclient.waveclient.mixininterface.ISimpleOption;
import net.minecraft.command.CommandSource;

public class FovCommand extends Command {
    public FovCommand() {
        super("fov", "Changes your fov.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("fov", IntegerArgumentType.integer(1, 180)).executes(context -> {
            ((ISimpleOption) (Object) mc.options.getFov()).wave$set(context.getArgument("fov", Integer.class));
            return SINGLE_SUCCESS;
        }));
    }
}
