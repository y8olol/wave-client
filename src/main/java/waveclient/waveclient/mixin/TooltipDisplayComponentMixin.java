/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import waveclient.waveclient.systems.modules.Modules;
import waveclient.waveclient.systems.modules.render.BetterTooltips;
import net.minecraft.component.type.TooltipDisplayComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TooltipDisplayComponent.class)
public abstract class TooltipDisplayComponentMixin {
    @ModifyExpressionValue(method = "shouldDisplay", at = @At(value = "FIELD", target = "Lnet/minecraft/component/type/TooltipDisplayComponent;hideTooltip:Z"))
    private boolean modifyHideTooltip(boolean original) {
        return original && !Modules.get().get(BetterTooltips.class).tooltip.get();
    }

    @ModifyExpressionValue(method = "shouldDisplay", at = @At(value = "INVOKE", target = "Ljava/util/SequencedSet;contains(Ljava/lang/Object;)Z"))
    private boolean modifyHiddenComponents(boolean original) {
        return original && !Modules.get().get(BetterTooltips.class).additional.get();
    }
}
