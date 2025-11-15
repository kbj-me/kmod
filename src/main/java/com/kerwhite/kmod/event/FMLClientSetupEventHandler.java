package com.kerwhite.kmod.event;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.only.client.ClientSkinCache;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = kmod.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class FMLClientSetupEventHandler
{
    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event)
    {
        ClientSkinCache.requestSkin(UUID.fromString("aae57967-89de-4961-83a1-832964af60a7"),(Rl)->{});
    }
}
