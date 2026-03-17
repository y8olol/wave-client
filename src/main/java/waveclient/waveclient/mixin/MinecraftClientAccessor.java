/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.resource.ResourceReloadLogger;
import net.minecraft.client.session.ProfileKeys;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.report.AbuseReportContext;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.util.ApiServices;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Accessor("currentFps")
    static int wave$getFps() {
        return 0;
    }

    @Mutable
    @Accessor("session")
    void wave$setSession(Session session);

    @Accessor("resourceReloadLogger")
    ResourceReloadLogger wave$getResourceReloadLogger();

    @Accessor("attackCooldown")
    int wave$getAttackCooldown();

    @Accessor("attackCooldown")
    void wave$setAttackCooldown(int attackCooldown);

    @Invoker("doAttack")
    boolean wave$leftClick();

    @Mutable
    @Accessor("profileKeys")
    void wave$setProfileKeys(ProfileKeys keys);

    @Mutable
    @Accessor("userApiService")
    void wave$setUserApiService(UserApiService apiService);

    @Mutable
    @Accessor("skinProvider")
    void wave$setSkinProvider(PlayerSkinProvider skinProvider);

    @Mutable
    @Accessor("socialInteractionsManager")
    void wave$setSocialInteractionsManager(SocialInteractionsManager socialInteractionsManager);

    @Mutable
    @Accessor("abuseReportContext")
    void wave$setAbuseReportContext(AbuseReportContext abuseReportContext);

    @Mutable
    @Accessor("gameProfileFuture")
    void wave$setGameProfileFuture(CompletableFuture<ProfileResult> future);

    @Mutable
    @Accessor("apiServices")
    void wave$setApiServices(ApiServices apiServices);

    @Invoker("handleInputEvents")
    void wave$handleInputEvents();
}
