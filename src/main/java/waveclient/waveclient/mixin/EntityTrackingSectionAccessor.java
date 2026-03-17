/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import net.minecraft.util.collection.TypeFilterableList;
import net.minecraft.world.entity.EntityTrackingSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityTrackingSection.class)
public interface EntityTrackingSectionAccessor {
    @Accessor("collection")
    <T> TypeFilterableList<T> wave$getCollection();
}
