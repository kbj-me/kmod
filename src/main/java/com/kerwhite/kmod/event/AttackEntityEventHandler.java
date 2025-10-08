package com.kerwhite.kmod.event;


import com.kerwhite.kmod.item.SpearItem;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.network.ModMessages;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = kmod.MODID)
public class AttackEntityEventHandler
{
    @SubscribeEvent
    public static void onAttackEntityEvent(AttackEntityEvent event)
    {
        Player p = event.getEntity();
        if(p.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SpearItem)
        {
            if(p.getItemInHand(InteractionHand.OFF_HAND)!=ItemStack.EMPTY)
            {
                event.setCanceled(true);
            }
        }
    }
}
