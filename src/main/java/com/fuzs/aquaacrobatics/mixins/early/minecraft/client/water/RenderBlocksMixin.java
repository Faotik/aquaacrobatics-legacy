package com.fuzs.aquaacrobatics.mixins.early.minecraft.client.water;

import net.minecraft.block.BlockCauldron;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.fuzs.aquaacrobatics.config.ConfigHandler;

@Mixin(RenderBlocks.class)
public abstract class RenderBlocksMixin {

    @Shadow
    private IBlockAccess blockAccess;

    @Inject(
        method = "renderBlockCauldron",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;renderFaceYPos(Lnet/minecraft/block/Block;DDD Lnet/minecraft/util/IIcon;)V",
            ordinal = 1 // second call, which is for the liquid
        ))
    private void setLiquidAlpha(BlockCauldron block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHandler.BlocksConfig.newWaterColors) {
            int color = Blocks.water.colorMultiplier(this.blockAccess, x, y, z);
            float r = (float) (color >> 16 & 255) / 255.0F;
            float g = (float) (color >> 8 & 255) / 255.0F;
            float b = (float) (color & 255) / 255.0F;
            Tessellator.instance.setColorOpaque_F(r, g, b);
        }
    }
}
