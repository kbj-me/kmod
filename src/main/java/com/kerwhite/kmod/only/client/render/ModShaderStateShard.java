package com.kerwhite.kmod.only.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;

public class ModShaderStateShard extends RenderStateShard.ShaderStateShard
{
    public static final ResourceLocation CUSTOM_PORTAL_SHADER = new ResourceLocation("kmod", "rendertype_end_portal");
    public static final ResourceProvider PROVIDER = Minecraft.getInstance().getResourceManager();
    public static final RenderStateShard.ShaderStateShard END_PORTAL = new ShaderStateShard(()->
    {
        try
        {
            return new ShaderInstance(PROVIDER,CUSTOM_PORTAL_SHADER, DefaultVertexFormat.POSITION);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    });
}
