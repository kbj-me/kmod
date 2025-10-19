package com.kerwhite.kmod.enchantment;

import com.kerwhite.kmod.item.SpearItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SpearProtection extends Enchantment
{
    public SpearProtection(Rarity pRarity, EquipmentSlot... pApplicableSlots)
    {
        super(pRarity, EnchantmentCategory.ARMOR,pApplicableSlots);
    }
    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 1 + (pEnchantmentLevel - 1) * 20;
    }
    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + 15;
    }
    @Override
    public boolean canEnchant(ItemStack p_44642_)
    {

        return p_44642_.getItem() instanceof SpearItem || super.canEnchant(p_44642_);
    }
}
