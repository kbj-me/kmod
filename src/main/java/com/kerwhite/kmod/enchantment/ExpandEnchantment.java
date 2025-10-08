package com.kerwhite.kmod.enchantment;

import com.kerwhite.kmod.item.SpearItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ExpandEnchantment extends Enchantment
{
    public ExpandEnchantment(Rarity pRarity,EquipmentSlot... pApplicableSlots)
    {
        super(pRarity, EnchantmentCategory.WEAPON,pApplicableSlots);
    }
    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 1 + (pEnchantmentLevel - 1) * 10;
    }
    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 15;
    }
    @Override
    public int getMaxLevel() { return 3; }

    @Override
    public boolean canEnchant(ItemStack p_44642_)
    {

        return p_44642_.getItem() instanceof SpearItem || super.canEnchant(p_44642_);
    }
}
