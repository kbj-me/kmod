package com.kerwhite.kmod.item;

import com.kerwhite.kmod.screen.ConfigScreen;
import com.kerwhite.kmod.screen.UniversalGuiOpenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

public class testitem extends Item {
    public testitem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant().setNoRepair());
    }
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext)
    {
        if(useOnContext.getLevel().isClientSide())
        {
            Level level = useOnContext.getLevel();
            Player player = useOnContext.getPlayer();
            BlockPos pos = useOnContext.getClickedPos();
            Minecraft.getInstance().setScreen(UniversalGuiOpenWrapper.newInstance(ConfigScreen.class).addArgs(Component.translatable("test"),player,level,pos).autoArgClass().build().getScreen());

            //Minecraft.getInstance().setScreen(UniversalGuiOpenWrapper.newInstance(ConfigScreen.class).addArgsClass(Component.class,Player.class,Level.class,BlockPos.class).addArgs(Component.translatable("test"),player,level,pos).build().getScreen());
        }
//        if(useOnContext.getLevel().isClientSide)
//        {
//            GuiOpenWrapper.openScreenTransportationScreen(UUID.fromString("380df991-f603-344c-a090-369bad2a924a"));
//        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player player, @NotNull InteractionHand pUsedHand) {
//        if(pLevel.isClientSide)
//        {
//            ScreenShotHelper.getPixelsAsScale(4,(kpixels)->
//            {
//                UniversalPacketWrapper.newInstance(KUpdatePixelPack.class).writeUUID(player.getUUID()).async_writeCustom((Consumer<FriendlyByteBuf>) kpixels::toBytes, (Consumer<UniversalPacketWrapper>) wrapper -> ModMessages.sendToServer((KUpdatePixelPack)wrapper.build()));
//            });
//        }
        return super.use(pLevel, player, pUsedHand);
    }
}