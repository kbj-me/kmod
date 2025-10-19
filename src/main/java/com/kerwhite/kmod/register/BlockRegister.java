package com.kerwhite.kmod.register;

import com.kerwhite.kmod.kmod;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
@SuppressWarnings("All")
public class BlockRegister
{
    public static final DeferredRegister<Block> K_BLOCKS= DeferredRegister.create(ForgeRegistries.BLOCKS, kmod.MODID);
    public static void RegisterBus(IEventBus bus)
    {
        K_BLOCKS.register(bus);
    }
}
