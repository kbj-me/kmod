package com.kerwhite.kmod.creativemodetab;

import com.kerwhite.kmod.register.ItemRegister;
import com.kerwhite.kmod.register.register;

public class KCreativeModeTabItems 
{
    public static void init()
    {

        KCreativeModeTab.addItem(register.ENERGY_TRANSPORTER_ITEM);
        KCreativeModeTab.addItem(register.ENERGYVIEWERITEM);
        KCreativeModeTab.addItem(ItemRegister.ENERGYVIEWERITEM);
        KCreativeModeTab.addItem(ItemRegister.WOODEN_SPEAR);
        KCreativeModeTab.addItem(ItemRegister.STONE_SPEAR);
        KCreativeModeTab.addItem(ItemRegister.IRON_SPEAR);
        KCreativeModeTab.addItem(ItemRegister.GOLDEN_SPEAR);
        KCreativeModeTab.addItem(ItemRegister.DIAMOND_SPEAR);
        KCreativeModeTab.addItem(ItemRegister.NETHERITE_SPEAR);
    }
}
