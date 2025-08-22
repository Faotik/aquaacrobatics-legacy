package com.fuzs.aquaacrobatics.mixinplugin;

import com.fuzs.aquaacrobatics.config.ConfigHandler;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

public enum Mixins implements IMixins {

    // spotless:off
    Vanilla(new MixinBuilder()
        .setPhase(Phase.EARLY)
        .addCommonMixins(
            "minecraft.EntityBoatMixin",
            "minecraft.EntityPlayerMixin",
            "minecraft.EntityPlayerMPMixin",
            "minecraft.EntityMixin",
            "minecraft.EntityLivingBaseMixin",
            "minecraft.EntityThrowableMixin",
            "minecraft.BiomeMixin",
            "minecraft.BlockLiquidMixin",
            // "minecraft.BlockMagmaMixin",
            "minecraft.BlockMyceliumMixin",
            // "minecraft.BlockSoulSandMixin",
            "minecraft.accessor.FluidAccessor",
            "minecraft.accessor.IEventBusAccessor")
        .addClientMixins(
            "minecraft.client.EntityPlayerSPMixin",
            "minecraft.client.EntityClientPlayerMPMixin",
            "minecraft.client.EntityRendererMixin",
            "minecraft.client.ItemRendererMixin",
            "minecraft.client.ModelBipedMixin",
            "minecraft.client.RenderBoatMixin",
            "minecraft.client.RenderPlayerMixin",
            "minecraft.client.EntityOtherPlayerMPMixin",
            "minecraft.client.PlayerControllerMPMixin"
        )),

    WaterColors(new MixinBuilder()
        .setPhase(Phase.EARLY).setApplyIf(() -> ConfigHandler.BlocksConfig.newWaterColors)
        .addClientMixins(
            "minecraft.client.water.ItemBlockMixin",
            "minecraft.client.water.BlockLiquidMixin",
            "minecraft.client.water.RenderBlocksMixin")),

    EntityItem(new MixinBuilder(" EntityItem")
        .addExcludedMod(TargetedMod.ITEMPHYSIC)
        .setPhase(Phase.EARLY)
        .addCommonMixins("minecraft.EntityItemMixin")),

    BlockGrass(new MixinBuilder(" BlockGrass")
        .addExcludedMod(TargetedMod.HODGEPODGE)
        .setPhase(Phase.EARLY)
        .addCommonMixins("minecraft.BlockGrassMixin"))
    ;
    // spotless:on

    private final MixinBuilder MixinBuilder;

    Mixins(MixinBuilder MixinBuilder) {
        this.MixinBuilder = MixinBuilder;
    }

    @Override
    public MixinBuilder getBuilder() {
        return MixinBuilder;
    }
}
