package com.kerwhite.kmod.jadeextra;


import com.kerwhite.kmod.block.BlockEnergyTransporter;
import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeKmod implements IWailaPlugin
{
    public static final ResourceLocation KWailaPlugin = new ResourceLocation("kmod", "kwailaplugin");
    @Override
    public void register(IWailaCommonRegistration registration)
    {
        registration.registerBlockDataProvider(ComponentProvider.INSTANCE, BlockEntityEnergyTransporter.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration)
    {
        registration.registerBlockComponent(ComponentProvider.INSTANCE,BlockEnergyTransporter.class);
    }

}
