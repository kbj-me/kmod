package com.kerwhite.kmod.event;

import com.kerwhite.kmod.creativemodetab.KCreativeModeTabItems;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.register.ItemRegister;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = kmod.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class FMLCommonSetupEventHandler
{
    @SubscribeEvent
    public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event)
    {
        ModMessages.register();
        event.enqueueWork(KCreativeModeTabItems::init);
        event.enqueueWork(()->KCreativeModeTabItems.addItem(ItemRegister.CAMERA));
        event.enqueueWork(()->KCreativeModeTabItems.addItem(ItemRegister.VIEWER));
    }
}
