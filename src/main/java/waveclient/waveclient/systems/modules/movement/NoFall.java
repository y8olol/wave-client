/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.systems.modules.movement;

import waveclient.waveclient.events.packets.PacketEvent;
import waveclient.waveclient.events.world.TickEvent;
import waveclient.waveclient.mixin.PlayerMoveC2SPacketAccessor;
import waveclient.waveclient.mixininterface.IPlayerMoveC2SPacket;
import waveclient.waveclient.mixininterface.IVec3d;
import waveclient.waveclient.pathing.PathManagers;
import waveclient.waveclient.settings.BoolSetting;
import waveclient.waveclient.settings.EnumSetting;
import waveclient.waveclient.settings.Setting;
import waveclient.waveclient.settings.SettingGroup;
import waveclient.waveclient.systems.modules.Categories;
import waveclient.waveclient.systems.modules.Module;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.utils.Utils;
import waveclient.waveclient.utils.entity.EntityUtils;
import waveclient.waveclient.utils.player.FindItemResult;
import waveclient.waveclient.utils.player.InvUtils;
import waveclient.waveclient.utils.player.PlayerUtils;
import waveclient.waveclient.utils.player.Rotations;
import waveclient.waveclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.attribute.EnvironmentAttributes;

import java.util.function.Predicate;

public class NoFall extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("The way you are saved from fall damage.")
        .defaultValue(Mode.Packet)
        .build()
    );

    private final Setting<PlacedItem> placedItem = sgGeneral.add(new EnumSetting.Builder<PlacedItem>()
        .name("placed-item")
        .description("Which block to place.")
        .defaultValue(PlacedItem.Bucket)
        .visible(() -> mode.get() == Mode.Place)
        .build()
    );

    private final Setting<PlaceMode> airPlaceMode = sgGeneral.add(new EnumSetting.Builder<PlaceMode>()
        .name("air-place-mode")
        .description("Whether place mode places before you die or before you take damage.")
        .defaultValue(PlaceMode.BeforeDeath)
        .visible(() -> mode.get() == Mode.AirPlace)
        .build()
    );

    private final Setting<Boolean> anchor = sgGeneral.add(new BoolSetting.Builder()
        .name("anchor")
        .description("Centers the player and reduces movement when using bucket or air place mode.")
        .defaultValue(true)
        .visible(() -> mode.get() != Mode.Packet)
        .build()
    );

    private final Setting<Boolean> antiBounce = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-bounce")
        .description("Disables bouncing on slime-block and bed upon landing.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> pauseOnMace = sgGeneral.add(new BoolSetting.Builder()
        .name("pause-on-mace")
        .description("Pauses NoFall when using a mace.")
        .defaultValue(true)
        .build()
    );

    private boolean placedWater;
    private BlockPos targetPos;
    private int timer;
    private boolean prePathManagerNoFall;

    public NoFall() {
        super(Categories.Movement, "no-fall", "Attempts to prevent you from taking fall damage.");
    }

    @Override
    public void onActivate() {
        prePathManagerNoFall = PathManagers.get().getSettings().getNoFall().get();
        if (mode.get() == Mode.Packet) PathManagers.get().getSettings().getNoFall().set(true);

        placedWater = false;
    }

    @Override
    public void onDeactivate() {
        PathManagers.get().getSettings().getNoFall().set(prePathManagerNoFall);
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (pauseOnMace.get() && mc.player.getMainHandStack().getItem() instanceof MaceItem) return;
        if (mc.player.getAbilities().creativeMode
            || !(event.packet instanceof PlayerMoveC2SPacket)
            || mode.get() != Mode.Packet
            || ((IPlayerMoveC2SPacket) event.packet).wave$getTag() == 1337) return;


        if (!Modules.get().isActive(Flight.class)) {
            if (mc.player.isGliding()) return;
            if (mc.player.getVelocity().y > -0.5) return;
            ((PlayerMoveC2SPacketAccessor) event.packet).wave$setOnGround(true);
        } else {
            ((PlayerMoveC2SPacketAccessor) event.packet).wave$setOnGround(true);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!Utils.canUpdate()) return;

        if (timer > 20) {
            placedWater = false;
            timer = 0;
        }

        if (mc.player.getAbilities().creativeMode) return;
        if (pauseOnMace.get() && mc.player.getMainHandStack().getItem() instanceof MaceItem) return;

        // Airplace mode
        if (mode.get() == Mode.AirPlace) {
            // Test if fall damage setting is valid
            if (!airPlaceMode.get().test((float) mc.player.fallDistance)) return;

            // Center and place block
            if (anchor.get()) PlayerUtils.centerPlayer();

            Rotations.rotate(mc.player.getYaw(), 90, Integer.MAX_VALUE, () -> {
                double preY = mc.player.getVelocity().y;
                ((IVec3d) mc.player.getVelocity()).wave$setY(0);

                BlockUtils.place(mc.player.getBlockPos().down(), InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof BlockItem), false, 0, true);

                ((IVec3d) mc.player.getVelocity()).wave$setY(preY);
            });
        }

        // Bucket mode
        else if (mode.get() == Mode.Place) {
            PlacedItem placedItem1 = mc.world.getEnvironmentAttributes().getAttributeValue(EnvironmentAttributes.WATER_EVAPORATES_GAMEPLAY) && placedItem.get() == PlacedItem.Bucket ? PlacedItem.PowderSnow : placedItem.get();
            if (mc.player.fallDistance > 3 && !EntityUtils.isAboveWater(mc.player)) {
                Item item = placedItem1.item;

                // Place
                FindItemResult findItemResult = InvUtils.findInHotbar(item);
                if (!findItemResult.found()) return;

                // Center player
                if (anchor.get()) PlayerUtils.centerPlayer();

                // Check if there is a block within 5 blocks
                BlockHitResult result = mc.world.raycast(new RaycastContext(mc.player.getEntityPos(), mc.player.getEntityPos().subtract(0, 5, 0), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));

                // Place
                if (result != null && result.getType() == HitResult.Type.BLOCK) {
                    targetPos = result.getBlockPos().up();
                    if (placedItem1 == PlacedItem.Bucket)
                        useItem(findItemResult, true, targetPos, true);
                    else {
                        useItem(findItemResult, placedItem1 == PlacedItem.PowderSnow, targetPos, false);
                    }
                }
            }

            // Remove placed
            if (placedWater) {
                timer++;
                if (mc.player.getBlockStateAtPos().getBlock() == placedItem1.block) {
                    useItem(InvUtils.findInHotbar(Items.BUCKET), false, targetPos, true);
                } else if (mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock() == Blocks.POWDER_SNOW && mc.player.fallDistance==0 && placedItem1.block==Blocks.POWDER_SNOW){ //check if the powder snow block is still there and the player is on the ground
                    useItem(InvUtils.findInHotbar(Items.BUCKET), false, targetPos.down(), true);
                }
            }
        }
    }

    public boolean cancelBounce() {
        return isActive() && antiBounce.get();
    }

    private void useItem(FindItemResult item, boolean placedWater, BlockPos blockPos, boolean interactItem) {
        if (!item.found()) return;

        if (interactItem) {
            Rotations.rotate(Rotations.getYaw(blockPos), Rotations.getPitch(blockPos), 10, true, () -> {
                if (item.isOffhand()) {
                    mc.interactionManager.interactItem(mc.player, Hand.OFF_HAND);
                } else {
                    InvUtils.swap(item.slot(), true);
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    InvUtils.swapBack();
                }
            });
        } else {
            BlockUtils.place(blockPos, item, true, 10, true);
        }

        this.placedWater = placedWater;
    }

    @Override
    public String getInfoString() {
        return mode.get().toString();
    }

    public enum Mode {
        Packet,
        AirPlace,
        Place
    }

    public enum PlacedItem {
        Bucket(Items.WATER_BUCKET, Blocks.WATER),
        PowderSnow(Items.POWDER_SNOW_BUCKET, Blocks.POWDER_SNOW),
        HayBale(Items.HAY_BLOCK, Blocks.HAY_BLOCK),
        Cobweb(Items.COBWEB, Blocks.COBWEB),
        SlimeBlock(Items.SLIME_BLOCK, Blocks.SLIME_BLOCK);

        private final Item item;
        private final Block block;

        PlacedItem(Item item, Block block) {
            this.item = item;
            this.block = block;
        }
    }

    public enum PlaceMode {
        BeforeDamage(height -> height > 2),
        BeforeDeath(height -> height > Math.max(PlayerUtils.getTotalHealth(), 2));

        private final Predicate<Float> fallHeight;

        PlaceMode(Predicate<Float> fallHeight) {
            this.fallHeight = fallHeight;
        }

        public boolean test(float fallheight) {
            return fallHeight.test(fallheight);
        }
    }
}
