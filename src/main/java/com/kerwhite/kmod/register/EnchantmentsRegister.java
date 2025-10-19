package com.kerwhite.kmod.register;

import com.kerwhite.kmod.enchantment.ExpandEnchantment;
import com.kerwhite.kmod.enchantment.QuickCoolEnchantment;
import com.kerwhite.kmod.enchantment.SpearProtection;
import com.kerwhite.kmod.kmod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@SuppressWarnings("All")
public class EnchantmentsRegister
{
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final DeferredRegister<Enchantment> K_ENCHANTMENTS= DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, kmod.MODID);
    public static final RegistryObject<Enchantment> QUICKCOOL = K_ENCHANTMENTS.register("quickcool",()->{return new QuickCoolEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);});
    public static final RegistryObject<Enchantment> EXPAND = K_ENCHANTMENTS.register("expand",()->{return new ExpandEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);});
    public static final RegistryObject<Enchantment> SPEAR_PROTECTION = K_ENCHANTMENTS.register("spear_protection",()->{return new SpearProtection(Enchantment.Rarity.RARE, ARMOR_SLOTS);});
    public static void RegisterBus(IEventBus bus)
    {
        K_ENCHANTMENTS.register(bus);
    }
}
