package com.kerwhite.kmod.only.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

public class ModShaderStateShard extends RenderStateShard.ShaderStateShard
{
    public static ShaderInstance instance = null;
    public static RenderStateShard.ShaderStateShard END_PORTAL = null;
    public static void registerState()
    {
        END_PORTAL= new ShaderStateShard(()->instance);

    }
}
