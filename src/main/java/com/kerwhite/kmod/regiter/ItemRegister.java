package com.kerwhite.kmod.regiter;

import com.kerwhite.kmod.item.EnergyViewerGUI;
import com.kerwhite.kmod.item.testitem;
import com.kerwhite.kmod.kmod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister
{
    public static final DeferredRegister<Item> K_ITEMS= DeferredRegister.create(ForgeRegistries.ITEMS, kmod.MODID);
    public static final RegistryObject<Item> ENERGYVIEWERITEM = K_ITEMS.register("energy_viewer_gui",()->{return new EnergyViewerGUI();});
    public static final RegistryObject<Item> ENERGYVIEWERITEM1 = K_ITEMS.register("energy_viewer_g",()->{return new testitem();});
    public static void RegisterBus(IEventBus bus)
    {
        K_ITEMS.register(bus);
    }
}
