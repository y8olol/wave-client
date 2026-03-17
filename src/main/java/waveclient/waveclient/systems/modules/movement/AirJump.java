/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.movement;

import waveclient.waveclient.events.wave.KeyEvent;
import waveclient.waveclient.events.world.TickEvent;
import waveclient.waveclient.settings.BoolSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.render.Freecam;
import waveclient.waveclient.utils.misc.input.KeyAction;
import meteordevelopment.orbit.EventHandler;

public class AirJump extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> maintainLevel = sgGeneral.add(new BoolSetting.Builder()
        .name("maintain-level")
        .description("Maintains your current Y level when holding the jump key.")
        .defaultValue(false)
        .build()
    );

    private int level;

    public AirJump() {
        super(Categories.Movement, "air-jump", "Lets you jump in the air.");
    }

    @Override
    public void onActivate() {
        level = mc.player.getBlockPos().getY();
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (Modules.get().isActive(Freecam.class) || mc.currentScreen != null || mc.player.isOnGround()) return;

        if (event.action != KeyAction.Press) return;

        if (mc.options.jumpKey.matchesKey(event.input)) {
            level = mc.player.getBlockPos().getY();
            mc.player.jump();
        }
        else if (mc.options.sneakKey.matchesKey(event.input)) {
            level--;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (Modules.get().isActive(Freecam.class) || mc.player.isOnGround()) return;

        if (maintainLevel.get() && mc.player.getBlockPos().getY() == level && mc.options.jumpKey.isPressed()) {
            mc.player.jump();
        }
    }
}
