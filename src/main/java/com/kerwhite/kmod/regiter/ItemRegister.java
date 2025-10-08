package com.kerwhite.kmod.regiter;

import com.kerwhite.kmod.item.EnergyViewerGUI;
import com.kerwhite.kmod.item.SpearItem;
import com.kerwhite.kmod.item.testitem;
import com.kerwhite.kmod.kmod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister
{
    public static final DeferredRegister<Item> K_ITEMS= DeferredRegister.create(ForgeRegistries.ITEMS, kmod.MODID);
    public static final RegistryObject<Item> ENERGYVIEWERITEM = K_ITEMS.register("energy_viewer_gui",()->{return new EnergyViewerGUI();});
    public static final RegistryObject<Item> ENERGYVIEWERITEM1 = K_ITEMS.register("energy_viewer_g",()->{return new testitem();});
    public static final RegistryObject<Item> WOODEN_SPEAR = K_ITEMS.register("wooden_spear",()->new SpearItem(Tiers.WOOD,3, -2.4F,new Item.Properties()));
    public static final RegistryObject<Item> STONE_SPEAR = K_ITEMS.register("stone_spear",()->new SpearItem(Tiers.STONE,3, -2.4F,new Item.Properties()));
    public static final RegistryObject<Item> IRON_SPEAR = K_ITEMS.register("iron_spear",()->new SpearItem(Tiers.IRON,3, -2.4F,new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SPEAR = K_ITEMS.register("golden_spear",()->new SpearItem(Tiers.GOLD,3, -2.4F,new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SPEAR = K_ITEMS.register("diamond_spear",()->new SpearItem(Tiers.DIAMOND,3, -2.4F,new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_SPEAR = K_ITEMS.register("netherite_spear",()->new SpearItem(Tiers.NETHERITE,3, -2.4F,new Item.Properties()));
    public static void RegisterBus(IEventBus bus)
    {
        K_ITEMS.register(bus);
    }
}
