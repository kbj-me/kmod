package com.kerwhite.kmod.item;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.kerwhite.kmod.network.KUpdatePacket;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.kerwhite.kmod.screen.GuiOpenWrapper;
import net.minecraft.core.BlockPos;
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

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class testitem extends Item {
    public testitem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant().setNoRepair());
    }
    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        if(useOnContext.getLevel().isClientSide)
        {
            BlockPos pos=useOnContext.getClickedPos();
            KUpdatePacket UPDATE = (KUpdatePacket) new UniversalPacketWrapper<>(KUpdatePacket.class).writeInt(1).writeUtf("2").writeBoolean(false).writeBoolean(true).writeBlockPos(pos).build();
            ModMessages.sendToServer(UPDATE);
            //GuiOpenWrapper.openTestScreen(useOnContext.getClickedPos());
        }

//        if(useOnContext.getLevel().isClientSide)
//        {
//            BlockPos pos = useOnContext.getClickedPos();
//            BlockEntity blockEntity = useOnContext.getLevel().getBlockEntity(pos);
//            if(blockEntity instanceof BlockEntityEnergyTransporter beet)
//            {
//                Item[] items = beet.getRelativeEnergyAbleBlockEntityWithListOfItems();
//                for(Item item : items)
//                {
//                    if(item != null)
//                    {
//                        System.out.println(item.getName(item.getDefaultInstance()).getString());
//                    }
//                }
//            }
//        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player player, @NotNull InteractionHand pUsedHand) {
        if(pLevel.isClientSide)
        {
            AtomicInteger height = new AtomicInteger();
            AtomicInteger width = new AtomicInteger();
            AtomicReference<List<Integer>> pixel = new AtomicReference<>();
            ScreenShotHelper.getPixels((kPixels)->
            {
                height.set(kPixels.getHeight());
                width.set(kPixels.getWidth());
                pixel.set(kPixels.getPixels());
            });
            GuiOpenWrapper.openFirstGui(height,width,pixel);
        }
        return super.use(pLevel, player, pUsedHand);
    }
}