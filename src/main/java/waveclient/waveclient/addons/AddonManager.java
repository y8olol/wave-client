/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.addons;

import waveclient.waveclient.WaveClient;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {
    public static final List<WaveAddon> ADDONS = new ArrayList<>();

    public static void init() {
        // Wave pseudo addon
        {
            WaveClient.ADDON = new WaveAddon() {
                @Override
                public void onInitialize() {}

                @Override
                public String getPackage() {
                    return "waveclient.waveclient";
                }

                @Override
                public GithubRepo getRepo() {
                    return new GithubRepo("WaveDevelopment", "wave-client");
                }

                @Override
                public String getCommit() {
                    String commit = WaveClient.MOD_META.getCustomValue(WaveClient.MOD_ID + ":commit").getAsString();
                    return commit.isEmpty() ? null : commit;
                }
            };

            ModMetadata metadata = FabricLoader.getInstance().getModContainer(WaveClient.MOD_ID).get().getMetadata();

            WaveClient.ADDON.name = metadata.getName();
            WaveClient.ADDON.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue(WaveClient.MOD_ID + ":color")) {
                WaveClient.ADDON.color.parse(metadata.getCustomValue(WaveClient.MOD_ID + ":color").getAsString());
            }

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                WaveClient.ADDON.authors[i++] = author.getName();
            }

            ADDONS.add(WaveClient.ADDON);
        }

        // Addons
        for (EntrypointContainer<WaveAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("wave", WaveAddon.class)) {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            WaveAddon addon;
            try {
                addon = entrypoint.getEntrypoint();
            } catch (Throwable throwable) {
                throw new RuntimeException("Exception during addon init \"%s\".".formatted(metadata.getName()), throwable);
            }

            addon.name = metadata.getName();

            if (metadata.getAuthors().isEmpty()) throw new RuntimeException("Addon \"%s\" requires at least 1 author to be defined in its fabric.mod.json.".formatted(addon.name));
            addon.authors = new String[metadata.getAuthors().size()];

            if (metadata.containsCustomValue(WaveClient.MOD_ID + ":color")) {
                addon.color.parse(metadata.getCustomValue(WaveClient.MOD_ID + ":color").getAsString());
            }

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                addon.authors[i++] = author.getName();
            }

            ADDONS.add(addon);
        }
    }
}
