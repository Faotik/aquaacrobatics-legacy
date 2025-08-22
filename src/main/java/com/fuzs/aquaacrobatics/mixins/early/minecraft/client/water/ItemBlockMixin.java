package com.fuzs.aquaacrobatics.mixins.early.minecraft.client.water;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.fuzs.aquaacrobatics.config.ConfigHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(ItemBlock.class)
public class ItemBlockMixin {

    /**
     * This method is used to get the color of the item block in the inventory.
     * If the block is water or flowing water, it will return the color based on the new water colors config.
     * Otherwise, it returns white (16777215).
     */
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int metadata) {
        if (ConfigHandler.BlocksConfig.newWaterColors && item != null && item.getItem() instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(item.getItem());

            if (block == Blocks.water || block == Blocks.flowing_water) {
                // Use the same logic from @ModifyReturnValue for water
                int baseColor = Blocks.water.colorMultiplier(Minecraft.getMinecraft().theWorld, 0, 0, 0);

                // Extract RGB
                int r = (baseColor >> 16) & 0xFF;
                int g = (baseColor >> 8) & 0xFF;
                int b = baseColor & 0xFF;

                return (r << 16) | (g << 8) | b;
            }
        }
        return 16777215;
    }

}
