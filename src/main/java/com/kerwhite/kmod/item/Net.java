package com.kerwhite.kmod.item;

import com.kerwhite.kmod.corelib.blockpos.BlockPosHelper;
import com.kerwhite.kmod.corelib.pipenetwork.FindNetHelper;
import com.kerwhite.kmod.corelib.pipenetwork.PipeNetWork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class Net extends Item
{

    public Net() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_)
    {
        if (!p_41432_.isClientSide())
        {
            System.out.println("PIPES:");
            FindNetHelper.getPipes();
        }
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41427_)
    {
        if (!p_41427_.getLevel().isClientSide())
        {
            System.out.println("DFS:");
            FindNetHelper.findnet();
        }

        return super.useOn(p_41427_);
    }
}
