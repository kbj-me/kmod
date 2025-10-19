package com.kerwhite.kmod.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.kerwhite.kmod.blockentitywithoutlevelrenderer.KBEWLR;
import com.kerwhite.kmod.register.EnchantmentsRegister;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class SpearItem extends TieredItem implements Vanishable
{
    private static final UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("a161b17c-5d2f-45c9-9313-2d4c3f1f5c2c");
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public SpearItem(Tier p_43269_, int p_43270_, float p_43271_, Item.Properties p_43272_)
    {
        super(p_43269_, p_43272_);
        this.attackDamage = (float)p_43270_ + p_43269_.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)p_43271_, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    public float getDamage() {
        return this.attackDamage;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        if(!level.isClientSide())
        {
            if(player.getItemInHand(InteractionHand.OFF_HAND)==ItemStack.EMPTY)
            {
                for(ItemStack item : player.getInventory().items)
                {
                    if(item != player.getItemInHand(InteractionHand.MAIN_HAND) && item.getItem() instanceof SpearItem)
                    {
                        item.getOrCreateTag().putInt("Mode",2);
                    }
                }
                CompoundTag tag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag();
                if(tag.getInt("Mode")==0){tag.putInt("Mode",1);}
                else if(tag.getInt("Mode") == 2){tag.putInt("Mode",1);}
                else if(tag.getInt("Mode") == 1){tag.putInt("Mode",2);}
            }

        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
    @Override
    public boolean canAttackBlock(@NotNull BlockState p_43291_, @NotNull Level p_43292_, @NotNull BlockPos p_43293_, @NotNull Player p_43294_)
    {
        return false;
    }
    @Override
    public float getDestroySpeed(@NotNull ItemStack p_43288_, BlockState p_43289_)
    {
        if (p_43289_.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            return p_43289_.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
        }
    }
    @Override
    public int getEnchantmentValue() {
        return 1;
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack)
    {
        return true; // 允许附魔
    }
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        if (enchantment == EnchantmentsRegister.QUICKCOOL.get()||enchantment == EnchantmentsRegister.EXPAND.get() ||enchantment == Enchantments.SHARPNESS || enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.MENDING||enchantment ==Enchantments.UNBREAKING )
        {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, @NotNull LivingEntity p_43279_, @NotNull LivingEntity p_43280_)
    {
        p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    @Override
    public boolean mineBlock(@NotNull ItemStack p_43282_, @NotNull Level p_43283_, BlockState p_43284_, @NotNull BlockPos p_43285_, @NotNull LivingEntity p_43286_)
    {
        if (p_43284_.getDestroySpeed(p_43283_, p_43285_) != 0.0F) {
            p_43282_.hurtAndBreak(2, p_43286_, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }
    @Override
    public boolean isCorrectToolForDrops(@NotNull BlockState p_43298_)
    {
        return false;
        //return p_43298_.is(Blocks.COBWEB);
    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot p_43274_)
    {
        return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
    }
    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction)
    {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new KBEWLR();
            }
        });
    }
}
