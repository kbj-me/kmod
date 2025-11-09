package com.kerwhite.kmod.item;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.kerwhite.kmod.network.packet.c2s.KUpdatePixelPack;
import com.kerwhite.kmod.screen.GuiOpenWrapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class testitem extends Item {
    public testitem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant().setNoRepair());
    }
    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        if(useOnContext.getLevel().isClientSide)
        {
            GuiOpenWrapper.openScreenTransportationScreen(UUID.fromString("380df991-f603-344c-a090-369bad2a924a"));
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player player, @NotNull InteractionHand pUsedHand) {
        if(pLevel.isClientSide)
        {
            ScreenShotHelper.getPixelsAsScale(4,(kpixels)->
            {
                UniversalPacketWrapper.newInstance(KUpdatePixelPack.class).writeUUID(player.getUUID()).async_writeCustom((Consumer<FriendlyByteBuf>) kpixels::toBytes, (Consumer<UniversalPacketWrapper>) wrapper -> ModMessages.sendToServer((KUpdatePixelPack)wrapper.build()));
            });
        }
        return super.use(pLevel, player, pUsedHand);
    }
}