/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.player;

import waveclient.waveclient.settings.BoolSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.settings.StringSetting;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;

public class NameProtect extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> nameProtect = sgGeneral.add(new BoolSetting.Builder()
        .name("name-protect")
        .description("Hides your name client-side.")
        .defaultValue(true)
        .build()
    );

    private final Setting<String> name = sgGeneral.add(new StringSetting.Builder()
        .name("name")
        .description("Name to be replaced with.")
        .defaultValue("seasnail")
        .visible(nameProtect::get)
        .build()
    );

    private final Setting<Boolean> skinProtect = sgGeneral.add(new BoolSetting.Builder()
        .name("skin-protect")
        .description("Make players become Steves.")
        .defaultValue(true)
        .build()
    );

    private String username = "If you see this, something is wrong.";

    public NameProtect() {
        super(Categories.Player, "name-protect", "Hide player names and skins.");
    }

    @Override
    public void onActivate() {
        username = mc.getSession().getUsername();
    }

    public String replaceName(String string) {
        if (string != null && isActive()) {
            return string.replace(username, name.get());
        }

        return string;
    }

    public String getName(String original) {
        if (!name.get().isEmpty() && isActive()) {
            return name.get();
        }

        return original;
    }

    public boolean skinProtect() {
        return isActive() && skinProtect.get();
    }
}
