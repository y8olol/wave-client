/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.movement;

import waveclient.waveclient.settings.EnumSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;

public class Sneak extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Which method to sneak.")
        .defaultValue(Mode.Vanilla)
        .build()
    );

    public Sneak() {
        super (Categories.Movement, "sneak", "Sneaks for you");
    }

    public boolean doPacket() {
        return isActive() && !mc.player.getAbilities().flying && mode.get() == Mode.Packet;
    }

    public boolean doVanilla() {
        return isActive() && !mc.player.getAbilities().flying && mode.get() == Mode.Vanilla;
    }

    public enum Mode {
        Packet,
        Vanilla
    }
}
