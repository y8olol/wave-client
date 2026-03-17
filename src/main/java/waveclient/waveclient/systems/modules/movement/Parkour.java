/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.movement;

import com.google.common.collect.Streams;
import waveclient.waveclient.events.world.TickEvent;
import waveclient.waveclient.settings.DoubleSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

import java.util.stream.Stream;

public class Parkour extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> edgeDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("edge-distance")
        .description("How far from the edge should you jump.")
        .range(0.001, 0.1)
        .defaultValue(0.001)
        .build()
    );

    public Parkour() {
        super(Categories.Movement, "parkour", "Automatically jumps at the edges of blocks.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if(!mc.player.isOnGround() || mc.options.jumpKey.isPressed()) return;

        if(mc.player.isSneaking() || mc.options.sneakKey.isPressed()) return;

        Box box = mc.player.getBoundingBox();
        Box adjustedBox = box.offset(0, -0.5, 0).expand(-edgeDistance.get(), 0, -edgeDistance.get());

        Stream<VoxelShape> blockCollisions = Streams.stream(mc.world.getBlockCollisions(mc.player, adjustedBox));

        if(blockCollisions.findAny().isPresent()) return;

        mc.player.jump();
    }
}
