package com.kerwhite.kmod.corelib.pipenetwork;

import com.kerwhite.kmod.corelib.blockpos.BlockPosHelper;
import net.minecraft.core.BlockPos;

import java.util.*;

public class PipeNetWork
{
    private static final Set<BlockPos> pipes = new HashSet<>();
    public static final Map<BlockPos,Boolean> visit = new HashMap<>();
    public static final Set<Set<BlockPos>> networks = new HashSet<>();
    public static void addBlockPos(BlockPos pos)
    {
        PipeNetWork.pipes.add(pos);
    }
    public static BlockPos fistPos()
    {
        return PipeNetWork.pipes.iterator().next();
    }
    public static boolean hasBlockPos(BlockPos pos)
    {
         return PipeNetWork.pipes.contains(pos);
    }
    public static void removeBlockPos(BlockPos pos)
    {
        PipeNetWork.pipes.remove(pos);
    }
    public static Iterator<BlockPos> getIterator()
    {
        return PipeNetWork.pipes.iterator();
    }
}
