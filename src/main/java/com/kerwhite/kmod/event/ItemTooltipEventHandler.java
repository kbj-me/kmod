package com.kerwhite.kmod.event;

import com.kerwhite.kmod.item.EnergyViewerGUI;
import com.kerwhite.kmod.item.EnergyViewer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("ALL")
@Mod.EventBusSubscriber(modid="kmod",bus= Mod.EventBusSubscriber.Bus.FORGE)
public class ItemTooltipEventHandler
{
    @SubscribeEvent
    public static void IItemTooltipEvent(ItemTooltipEvent event)
    {
        ItemStack itemStack = event.getItemStack();
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        int mode = compoundTag.getInt("Mode");
        Component meg = null;
        if(itemStack.getItem() instanceof EnergyViewer)
        {
            meg = switch (mode)
            {
                case 1 -> Component.translatable("lang.kmod.energyviewer.tooltip.1");
                case 2 -> Component.translatable("lang.kmod.energyviewer.tooltip.2");
                case 3 -> Component.translatable("lang.kmod.energyviewer.tooltip.3");
                case 4 -> Component.translatable("lang.kmod.energyviewer.tooltip.4");
                default -> Component.translatable("lang.kmod.energyviewer.tooltip.err");
            };
            if(mode != 0)
            {event.getToolTip().add(Component.literal("").append(meg).withStyle(ChatFormatting.YELLOW));}
            else {event.getToolTip().add(Component.literal("").withStyle(ChatFormatting.YELLOW).append(Component.translatable("lang.kmod.energyviewer.tooltip.def")));}
            event.getToolTip().add(Component.literal("").append(Component.translatable("lang.kmod.use.1").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BLUE)));
            event.getToolTip().add(Component.literal("").append(Component.translatable("lang.kmod.use.2").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BLUE)));

        }
        if(itemStack.getItem() instanceof EnergyViewerGUI)
        {
            event.getToolTip().add(Component.literal("").append(Component.translatable("lang.kmod.use.gui").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BLUE)));
        }
    }

}
