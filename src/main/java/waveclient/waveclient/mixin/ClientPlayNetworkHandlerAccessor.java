/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.message.MessageChain;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayNetworkHandler.class)
public interface ClientPlayNetworkHandlerAccessor {
    @Accessor("chunkLoadDistance")
    int wave$getChunkLoadDistance();

    @Accessor("messagePacker")
    MessageChain.Packer wave$getMessagePacker();

    @Accessor("lastSeenMessagesCollector")
    LastSeenMessagesCollector wave$getLastSeenMessagesCollector();

    @Accessor("combinedDynamicRegistries")
    DynamicRegistryManager.Immutable wave$getCombinedDynamicRegistries();

    @Accessor("enabledFeatures")
    FeatureSet wave$getEnabledFeatures();

    @Accessor("COMMAND_NODE_FACTORY")
    static CommandTreeS2CPacket.NodeFactory<ClientCommandSource> wave$getCommandNodeFactory() {
        return null;
    }
}
