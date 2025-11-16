package com.kerwhite.kmod.event;

import com.kerwhite.kmod.ModRenderType;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.only.client.render.ModShaderStateShard;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = kmod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterShadersEventHandler
{
    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException
    {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("kmod","end_portal"), DefaultVertexFormat.POSITION), shaderInstance ->
        {
            ModShaderStateShard.instance = shaderInstance;
            ModShaderStateShard.registerState();
            ModRenderType.registerRenderType();
        });
    }
}
