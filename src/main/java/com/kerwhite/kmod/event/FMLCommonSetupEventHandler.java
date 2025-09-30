package com.kerwhite.kmod.event;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.network.ModMessages;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = kmod.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class FMLCommonSetupEventHandler
{
    @SubscribeEvent
    public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event)
    {
        ModMessages.register();
    }
}
