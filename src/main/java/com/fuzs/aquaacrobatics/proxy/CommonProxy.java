package com.fuzs.aquaacrobatics.proxy;

import net.minecraftforge.common.MinecraftForge;
import com.fuzs.aquaacrobatics.AquaAcrobatics;
import com.fuzs.aquaacrobatics.biome.BiomeWaterFogColors;
import com.fuzs.aquaacrobatics.config.ConfigHandler;
import com.fuzs.aquaacrobatics.handler.CommonHandler;
import com.fuzs.aquaacrobatics.integration.IntegrationManager;
import com.fuzs.aquaacrobatics.integration.hats.HatsIntegration;
import com.fuzs.aquaacrobatics.network.NetworkHandler;
import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@EventBusSubscriber
public class CommonProxy {

    private boolean needNetworking() {
        return ConfigHandler.MovementConfig.enableToggleCrawling;
    }

    public void onPreInit(FMLPreInitializationEvent event) {
        IntegrationManager.loadCompat();
        if (needNetworking())
            NetworkHandler.registerMessages(AquaAcrobatics.MODID);
        MinecraftForge.EVENT_BUS.register(new CommonHandler());
    }

    public void onInit() {

    }

    public void onMappings() {

    }

    public void onPostInit() {

        if (IntegrationManager.isHatsEnabled()) {

            HatsIntegration.register();
        }

        BiomeWaterFogColors.recomputeColors();
        // This code will print a warning if we don't have a color mapping for the biome
        /*
         * for(BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
         *  biome.getWaterColorMultiplier();
         * }
         */
    }

}
