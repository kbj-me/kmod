package com.kerwhite.kmod.corelib.pipenetwork;

import com.kerwhite.kmod.corelib.blockpos.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.Iterator;

public class FindNetHelper
{
    public static final Direction[] directions = {Direction.SOUTH,Direction.NORTH,Direction.EAST,Direction.WEST,Direction.UP,Direction.DOWN};
    public static void dfs(BlockPos pos)
    {
        PipeNetWork.visit.put(pos,Boolean.TRUE);
        BlockPos newpos;
        BlockPosHelper.out(pos);
        for(Direction i : directions)
        {
            newpos = pos.relative(i);
            if(PipeNetWork.visit.get(newpos)==null)
            {
                PipeNetWork.visit.put(newpos,Boolean.FALSE);
            }
            if(PipeNetWork.hasBlockPos(newpos) && PipeNetWork.visit.get(newpos)==Boolean.FALSE)
            {
                dfs(newpos);
            }
        }
    }
    public static void findnet()
    {
        Iterator<BlockPos> iterator = PipeNetWork.getIterator();
        PipeNetWork.visit.clear();
        while(iterator.hasNext())
        {
            BlockPos pos = iterator.next();
            if(PipeNetWork.visit.get(pos)==Boolean.FALSE || PipeNetWork.visit.get(pos)==null)
            {
                dfs(pos);
                System.out.println("============");
            }
        }
    }
    public static void getPipes()
    {
        Iterator<BlockPos> iterator = PipeNetWork.getIterator();
        while(iterator.hasNext())
        {
            BlockPos pos = iterator.next();
            BlockPosHelper.out(pos);
        }
    }
}
