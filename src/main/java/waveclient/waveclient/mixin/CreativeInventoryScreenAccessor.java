/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreativeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor {
    @Accessor("selectedTab")
    static ItemGroup wave$getSelectedTab() {
        return null;
    }
}
