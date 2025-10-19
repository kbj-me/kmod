package com.kerwhite.kmod.corelib.blockpos;

import net.minecraft.core.BlockPos;

public class BlockPosHelper
{
    public static BlockPos newPos(int x,int y,int z)
    {
        return new BlockPos(x,y,z);
    }
    public static void out(BlockPos pos)
    {
       System.out.print(pos.getX());
       System.out.print("|");
       System.out.print(pos.getY());
       System.out.print("|");
       System.out.println(pos.getZ());
    }
}
