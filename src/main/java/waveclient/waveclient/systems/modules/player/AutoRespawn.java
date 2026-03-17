/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.player;

import waveclient.waveclient.events.game.OpenScreenEvent;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.render.WaypointsModule;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super(Categories.Player, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) return;

        Modules.get().get(WaypointsModule.class).addDeath(mc.player.getEntityPos());
        mc.player.requestRespawn();
        event.cancel();
    }
}
