package com.kerwhite.kmod.item;

import com.kerwhite.kmod.network.KUpdatePacket;
import com.kerwhite.kmod.network.KUpdatePacketWrapper;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.screen.GuiOpenWrapper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class testitem extends Item {
    public testitem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant().setNoRepair());
    }
    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        BlockPos pos = useOnContext.getClickedPos();
        KUpdatePacket UPDATE = new KUpdatePacketWrapper().writeInt(1).writeUtf("2").writeBoolean(false).writeBoolean(true).writeBlockPos(pos).build();
        ModMessages.sendToServer(UPDATE);
        return InteractionResult.SUCCESS;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player player, @NotNull InteractionHand pUsedHand) {
        if(pLevel.isClientSide)
        {
            GuiOpenWrapper.openTestScreen();
        // GuiOpenWrapper.openFirstGui(pPlayer);
        // ModMessages.sendToServer(new KUpdatePacket());
        }

        return super.use(pLevel, player, pUsedHand);
    }
}