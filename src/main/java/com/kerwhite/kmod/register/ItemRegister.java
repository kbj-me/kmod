package com.kerwhite.kmod.register;

import com.kerwhite.kmod.item.*;
import com.kerwhite.kmod.kmod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@SuppressWarnings("All")
public class ItemRegister
{
    public static final DeferredRegister<Item> K_ITEMS= DeferredRegister.create(ForgeRegistries.ITEMS, kmod.MODID);
    public static final RegistryObject<Item> ENERGYVIEWERITEM = K_ITEMS.register("energy_viewer_gui",()->{return new EnergyViewerGUI();});
    public static final RegistryObject<Item> ENERGYVIEWERITEM1 = K_ITEMS.register("energy_viewer_g",()->{return new testitem();});
    public static final RegistryObject<Item> NET = K_ITEMS.register("net",()->{return new Net();});
    public static final RegistryObject<Item> LAZER = K_ITEMS.register("lazer",()->{return new Lazer();});
    public static final RegistryObject<Item> NETADD = K_ITEMS.register("netadd",()->{return new addnet();});
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
