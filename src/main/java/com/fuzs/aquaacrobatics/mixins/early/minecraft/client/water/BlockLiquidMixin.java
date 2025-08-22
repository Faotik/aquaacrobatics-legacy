package com.fuzs.aquaacrobatics.mixins.early.minecraft.client.water;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.fuzs.aquaacrobatics.config.ConfigHandler;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(BlockLiquid.class)
public class BlockLiquidMixin {

    @ModifyConstant(method = "registerBlockIcons", constant = @Constant(stringValue = "water_still"))
    private String getWaterStillTexture(String old) {
        if (ConfigHandler.BlocksConfig.newWaterColors) return "aquaacrobatics:water_still";
        else return old;
    }

    @ModifyConstant(method = "registerBlockIcons", constant = @Constant(stringValue = "water_flow"))
    private String getWaterFlowTexture(String old) {
        if (ConfigHandler.BlocksConfig.newWaterColors) return "aquaacrobatics:water_flow";
        else return old;
    }

    @ModifyReturnValue(method = "colorMultiplier", at = @At(value = "RETURN"))
    private int modifyWaterColor(int original, IBlockAccess worldIn, int x, int y, int z) {
        if (ConfigHandler.BlocksConfig.newWaterColors && worldIn.getBlock(x, y, z)
            .getMaterial() == Material.water) {
            int color = original;

            float r = (float) (color >> 16 & 255) / 255.0F;
            float g = (float) (color >> 8 & 255) / 255.0F;
            float b = (float) (color & 255) / 255.0F;

            // Modify the transparency (alpha value) to make the water more transparent
            float alpha = 0.1F;
            int modifiedColor = ((int) (r * 255) << 16) | ((int) (g * 255) << 8)
                | ((int) (b * 255))
                | ((int) (alpha * 255) << 24);

            return modifiedColor;
        }
        return original;
    }
}
