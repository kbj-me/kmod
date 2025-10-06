package com.kerwhite.kmod.item;

import com.kerwhite.kmod.network.KRequestPack;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.regiter.ItemRegister;
import com.kerwhite.kmod.screen.GuiOpenWrapper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import java.io.Serial;
import java.security.KeyRep;

public class testitem extends Item {
    public testitem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant().setNoRepair());
    }
    @Override
    public InteractionResult useOn(UseOnContext useOnContext)
    {

        return InteractionResult.SUCCESS;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        //if(pLevel.isClientSide)
        //{
        // GuiOpenWrapper.openFirstGui(pPlayer);
        // ModMessages.sendToServer(new KUpdatePacket());
        //}

        return super.use(pLevel, player, pUsedHand);
    }
}