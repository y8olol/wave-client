/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.movement.speed.modes;

import waveclient.waveclient.events.entity.player.PlayerMoveEvent;
import waveclient.waveclient.mixininterface.IVec3d;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.movement.Anchor;
import waveclient.waveclient.systems.modules.movement.speed.SpeedMode;
import waveclient.waveclient.systems.modules.movement.speed.SpeedModes;
import waveclient.waveclient.utils.player.PlayerUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

public class Vanilla extends SpeedMode {
    public Vanilla() {
        super(SpeedModes.Vanilla);
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        Vec3d vel = PlayerUtils.getHorizontalVelocity(settings.vanillaSpeed.get());
        double velX = vel.getX();
        double velZ = vel.getZ();

        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            double value = (mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1) * 0.205;
            velX += velX * value;
            velZ += velZ * value;
        }

        Anchor anchor = Modules.get().get(Anchor.class);
        if (anchor.isActive() && anchor.controlMovement) {
            velX = anchor.deltaX;
            velZ = anchor.deltaZ;
        }

        ((IVec3d) event.movement).wave$set(velX, event.movement.y, velZ);
    }
}
