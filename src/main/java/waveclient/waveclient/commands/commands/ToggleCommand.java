/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import waveclient.waveclient.commands.Command;
import waveclient.waveclient.commands.arguments.ModuleArgumentType;
import waveclient.waveclient.systems.hud.Hud;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

import java.util.ArrayList;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Toggles a module.", "t");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder
            .then(literal("all")
                .then(literal("on")
                    .executes(context -> {
                        new ArrayList<>(Modules.get().getAll()).forEach(Module::enable);
                        Hud.get().active = true;
                        return SINGLE_SUCCESS;
                    })
                )
                .then(literal("off")
                    .executes(context -> {
                        new ArrayList<>(Modules.get().getActive()).forEach(Module::toggle);
                        Hud.get().active = false;
                        return SINGLE_SUCCESS;
                    })
                )
            )
            .then(argument("module", ModuleArgumentType.create())
                .executes(context -> {
                    Module m = ModuleArgumentType.get(context);
                    m.toggle();
                    m.sendToggledMsg();
                    return SINGLE_SUCCESS;
                })
                .then(literal("on")
                    .executes(context -> {
                        Module m = ModuleArgumentType.get(context);
                        m.enable();
                        return SINGLE_SUCCESS;
                    }))
                .then(literal("off")
                    .executes(context -> {
                        Module m = ModuleArgumentType.get(context);
                        m.disable();
                        return SINGLE_SUCCESS;
                    })
                )
            )
            .then(literal("hud")
                .executes(context -> {
                    Hud.get().active = !(Hud.get().active);
                    return SINGLE_SUCCESS;
                })
                .then(literal("on")
                    .executes(context -> {
                        Hud.get().active = true;
                        return SINGLE_SUCCESS;
                    })
                ).then(literal("off")
                    .executes(context -> {
                        Hud.get().active = false;
                        return SINGLE_SUCCESS;
                    })
                )
            );
    }
}
