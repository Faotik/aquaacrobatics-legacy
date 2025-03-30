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
import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;

@SuppressWarnings("unused")
@EventBusSubscriber(side = Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        try {
            ConfigurationManager.registerConfig(ConfigHandler.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new AirMeterHandler());
        MinecraftForge.EVENT_BUS.register(new FogHandler());

        if (ConfigHandler.BlocksConfig.newWaterColors) {
            // todo fix this
            // IResourcePack resourcePack = new WaterResourcePack(event.getSourceFile());
            // List<IResourcePack> packs = ReflectionHelper
            // .getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao");
            // packs.add(resourcePack);
            // IResourceManager resMan = Minecraft.getMinecraft()
            // .getResourceManager();
            // if (resMan instanceof SimpleReloadableResourceManager && resourcePack != null) {
            // ((SimpleReloadableResourceManager) resMan).reloadResourcePack(resourcePack);
            // }
            OptifineHelper.init();
        }
    }

    @Override
    public void onInit() {Keybindings.register();}

    @Override
    public void onMappings() {
        if (OptifineHelper.isOFPresent) {
            OptifineHelper.reloadBlockAliases();
        }
    }


    @SubscribeEvent
    public static void registerTextures(TextureStitchEvent.Pre event) {
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
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (ConfigHandler.MovementConfig.enableToggleCrawling && Keybindings.forceCrawling.getIsKeyPressed()) {
            IPlayerResizeable player = (IPlayerResizeable) Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                if (player.canForceCrawling()) NetworkHandler.INSTANCE
                    .sendToServer(new PacketSendKey(PacketSendKey.KeybindPacket.TOGGLE_CRAWLING));
                else {
                    ((EntityPlayerSP) player).addChatMessage(new ChatComponentTranslation("chat.aquaacrobatics.cannot_toggle_crawling"));
                }
            }
        }
    }

    @Override
    public void onPostInit() {

        super.onPostInit();
        FogHandler.recomputeBlacklist();
    }

}
