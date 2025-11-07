package com.kerwhite.kmod;

import com.kerwhite.kmod.blockentitywithoutlevelrenderer.KBEWLR;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ClientItemExtensions implements IClientItemExtensions
{
// 在字段中缓存我们的 BEWLR。
    private final KBEWLR myBEWLR = new KBEWLR();

// 在此处返回我们的 BEWLR。
    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer()
    {
        return myBEWLR;
    }
}