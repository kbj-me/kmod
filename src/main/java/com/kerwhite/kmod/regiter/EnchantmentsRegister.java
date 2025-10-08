package com.kerwhite.kmod.regiter;

import com.kerwhite.kmod.enchantment.ExpandEnchantment;
import com.kerwhite.kmod.enchantment.QuickCoolEnchantment;
import com.kerwhite.kmod.item.EnergyViewerGUI;
import com.kerwhite.kmod.kmod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentsRegister
{
    public static final DeferredRegister<Enchantment> K_ENCHANTMENTS= DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, kmod.MODID);
    public static final RegistryObject<Enchantment> QUICKCOOL = K_ENCHANTMENTS.register("quickcool",()->{return new QuickCoolEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);});
    public static final RegistryObject<Enchantment> EXPAND = K_ENCHANTMENTS.register("expand",()->{return new ExpandEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);});
    public static void RegisterBus(IEventBus bus)
    {
        K_ENCHANTMENTS.register(bus);
    }
}
