package com.fuzs.aquaacrobatics.mixins.early.minecraft.client;

import com.fuzs.aquaacrobatics.entity.Pose;
import com.fuzs.aquaacrobatics.entity.player.IPlayerResizeable;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ActiveRenderInfo.class)
public class ActiveRenderInfoMixin {

    @Redirect(method = "projectViewFromEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getEyeHeight()F"))
    private static float redirectGetEyeHeight(EntityLivingBase instance) {
        // it Doesn't like it when GetEyeHeight is -1.22
        if (instance instanceof IPlayerResizeable playerResizeable) {
            return (playerResizeable.getPose() == Pose.SWIMMING) ? instance.getEyeHeight() + 1.22F: instance.getEyeHeight();
        }
        return instance.getEyeHeight();
    }
}
