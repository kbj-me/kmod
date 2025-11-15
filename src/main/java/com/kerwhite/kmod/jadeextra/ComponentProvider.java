package com.kerwhite.kmod.jadeextra;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
public enum ComponentProvider implements IBlockComponentProvider,IServerDataProvider<BlockAccessor>
{
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip,BlockAccessor accessor,IPluginConfig config)
    {
        IElementHelper elements = IElementHelper.get();
        IElement icon = elements.item(new ItemStack(Items.BEACON), 0.5f).size(new Vec2(10, 10)).translate(new Vec2(0, -1));
        if(accessor.getServerData().contains("BindPlayer"))
        {
            tooltip.add(icon);
            tooltip.append(Component.translatable("kmod.bindplayer").append(accessor.getServerData().getString("BindPlayer")));
        }
        if(accessor.getServerData().contains("IsPlayerMode"))
        {
            tooltip.add(icon);
            boolean isplayermode = accessor.getServerData().getBoolean("IsPlayerMode");
            tooltip.append(Component.translatable("kmod.isplayermode").append(isplayermode?Component.literal("True"):Component.literal("False")));
        }
        if(accessor.getServerData().contains("IsOut"))
        {
            tooltip.add(icon);
            boolean isplayermode = accessor.getServerData().getBoolean("IsOut");
            tooltip.append(Component.translatable("kmod.isout").append(isplayermode?Component.literal("True"):Component.literal("False")));
        }
        if(accessor.getServerData().contains("MaxIO"))
        {
            tooltip.add(icon);
            tooltip.append(Component.translatable("kmod.maxio").append(Component.literal(String.valueOf(accessor.getServerData().getInt("MaxIO")))));
        }
    }
    @Override
    public ResourceLocation getUid()
    {
        return JadeKmod.KWailaPlugin;
    }
    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor)
    {
        BlockEntityEnergyTransporter beet = ((BlockEntityEnergyTransporter)accessor.getBlockEntity());
        data.putString("BindPlayer",beet.bindPlayer);
        data.putBoolean("IsPlayerMode",beet.isPlayerMode);
        data.putInt("MaxIO",beet.maxIO);
        data.putBoolean("IsOut",beet.isOut);

    }
    @Override
    public int getDefaultPriority()
    {
        return 500;
    }
}
