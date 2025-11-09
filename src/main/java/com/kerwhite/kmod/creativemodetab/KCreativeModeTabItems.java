package com.kerwhite.kmod.creativemodetab;

import com.kerwhite.kmod.register.ItemRegister;
import com.kerwhite.kmod.register.register;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class KCreativeModeTabItems 
{
    public static void init()
    {

        KCreativeModeTabItems.addItem(register.ENERGY_TRANSPORTER_ITEM);
        KCreativeModeTabItems.addItem(register.ENERGYVIEWERITEM);
        KCreativeModeTabItems.addItem(ItemRegister.ENERGYVIEWERITEM);
        KCreativeModeTabItems.addItem(ItemRegister.WOODEN_SPEAR);
        KCreativeModeTabItems.addItem(ItemRegister.STONE_SPEAR);
        KCreativeModeTabItems.addItem(ItemRegister.IRON_SPEAR);
        KCreativeModeTabItems.addItem(ItemRegister.GOLDEN_SPEAR);
        KCreativeModeTabItems.addItem(ItemRegister.DIAMOND_SPEAR);
        KCreativeModeTabItems.addItem(ItemRegister.NETHERITE_SPEAR);

    }
    public static void addItem(RegistryObject<Item> item)
    {
        if(item!=null)
        {
            KCreativeModeTab.addItem(item);
        }
    }
}
