package com.kerwhite.kmod.event;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.kerwhite.kmod.kmod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = kmod.MODID)
public class BlockEvent$EntityPlaceEvent
{
    @SubscribeEvent
    public static void onplace(BlockEvent.EntityPlaceEvent event)
    {
        Entity entity = event.getEntity();
        LevelAccessor level = event.getLevel();
        BlockPos blockpos = event.getPos();
        if(entity instanceof Player player)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockpos);
            if(blockEntity instanceof BlockEntityEnergyTransporter energyTransporter)
            {
                energyTransporter.setBindPlayer(player.getName().toString());
            }
        }
    }
}
