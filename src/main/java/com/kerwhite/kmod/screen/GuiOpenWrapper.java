package com.kerwhite.kmod.screen;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;


public class GuiOpenWrapper
{
    //BlockPos pos = new BlockPos(0,0,0);
    public static void openFirstGui(Player player, Level level , BlockPos pos)
    {
        Minecraft.getInstance().setScreen(new ConfigScreen(Component.translatable("test"),player,level,pos));
    }
    public static void openScreenTransportationScreen(UUID uuid)
    {
        Minecraft.getInstance().setScreen(new ScreenTransportationScreen(uuid));
    }
    public static void openTestScreen(BlockPos pos)
    {
        Minecraft.getInstance().setScreen(new TestScreen(Component.literal("test"),pos));
    }
}