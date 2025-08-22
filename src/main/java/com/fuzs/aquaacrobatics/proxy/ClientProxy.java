package com.fuzs.aquaacrobatics.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.fuzs.aquaacrobatics.client.handler.AirMeterHandler;
import com.fuzs.aquaacrobatics.client.handler.FogHandler;
import com.fuzs.aquaacrobatics.config.ConfigHandler;
import com.fuzs.aquaacrobatics.entity.player.IPlayerResizeable;
import com.fuzs.aquaacrobatics.network.NetworkHandler;
import com.fuzs.aquaacrobatics.network.message.PacketSendKey;
import com.fuzs.aquaacrobatics.optifine.OptifineHelper;
import com.fuzs.aquaacrobatics.util.Keybindings;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new AirMeterHandler());
        MinecraftForge.EVENT_BUS.register(new FogHandler());
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance()
            .bus()
            .register(this);

        // if (ConfigHandler.BlocksConfig.newWaterColors) {
        // Minecraft mc = Minecraft.getMinecraft();
        // WaterResourcePack waterPack = new WaterResourcePack(event.getSourceFile());
        // // Add it to Minecraft's default resource packs list
        // mc.defaultResourcePacks.add(waterPack);
        // OptifineHelper.init();
        // }
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        Keybindings.register();
    }

    @Override
    public void onMappings() {
        if (OptifineHelper.isOFPresent) {
            OptifineHelper.reloadBlockAliases();
        }
    }

    @SubscribeEvent
    public void registerTextures(TextureStitchEvent.Pre event) {
        if (ConfigHandler.BlocksConfig.newWaterColors) {
            TextureMap map = event.map;
            /* Register the custom 1.13-style texture used by most in-world renderers */
            if (map.getTextureType() == 0) {
                map.registerIcon("aquaacrobatics:water_still");
                map.registerIcon("aquaacrobatics:water_flow");
            }
        }
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (ConfigHandler.MovementConfig.enableToggleCrawling && Keybindings.forceCrawling.getIsKeyPressed()) {
            IPlayerResizeable player = (IPlayerResizeable) Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                if (player.canForceCrawling()) NetworkHandler.INSTANCE
                    .sendToServer(new PacketSendKey(PacketSendKey.KeybindPacket.TOGGLE_CRAWLING));
                else {
                    ((EntityPlayerSP) player)
                        .addChatMessage(new ChatComponentTranslation("chat.aquaacrobatics.cannot_toggle_crawling"));
                }
            }
        }
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
        FogHandler.recomputeBlacklist();
    }

}
