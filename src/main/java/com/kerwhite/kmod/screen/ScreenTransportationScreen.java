package com.kerwhite.kmod.screen;

import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.kerwhite.kmod.network.packet.c2s.KRequestPixelPack;
import com.kerwhite.kmod.only.client.ClientPixelCache;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ScreenTransportationScreen extends Screen
{
    AtomicInteger pheight;
    AtomicInteger pwitch;
    AtomicReference<List<Integer>> ppixel;
    UUID uuid;
    @Override
    protected void init()
    {
        ClientPixelCache.set(null);
        super.init();
    }
    @Override
    public void tick()
    {
        ModMessages.sendToServer((KRequestPixelPack) new UniversalPacketWrapper<>(KRequestPixelPack.class).writeUUID(uuid).build());
        super.tick();
    }
    protected ScreenTransportationScreen(UUID uuid)
    {
        super(Component.literal("ScreenTransportationScreen"));
        this.uuid = uuid;
    }
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_)
    {
        this.renderBackground(guiGraphics);
        if(ClientPixelCache.get()!=null)
        {
            this.pheight = new AtomicInteger(ClientPixelCache.get().getHeight());
            this.pwitch = new AtomicInteger(ClientPixelCache.get().getWidth());
            this.ppixel = new AtomicReference<>(ClientPixelCache.get().getPixels());
        }
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        guiGraphics.pose().pushPose();
        if(this.ppixel != null && this.pheight != null && this.pwitch != null)
        {
            float scaleX = (float) this.width / this.pwitch.get();
            float scaleY = (float) this.height / this.pheight.get();
            guiGraphics.pose().scale(scaleX, scaleY, 1.0f);
        }
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        if(this.ppixel != null && this.pheight != null && this.pwitch != null)
        {
            Iterator<Integer> i = this.ppixel.get().iterator();
            for (int y = 0; y < this.pheight.get(); y++)
            {
                for (int x = 0; x < this.pwitch.get(); x++)
                {
                    int k = i.next();
                    int red = (k >> 16) & 0xFF;
                    int green = (k >> 8) & 0xFF;
                    int blue = k & 0xFF;
                    float r = red / 255.0F;
                    float g = green / 255.0F;
                    float b = blue / 255.0F;
                    float a = 1.0f;
                    bufferBuilder.vertex(guiGraphics.pose().last().pose(), x, y + 1, 0).color(r,g,b,a).endVertex();
                    bufferBuilder.vertex(guiGraphics.pose().last().pose(), x + 1, y + 1, 0).color(r,g,b,a).endVertex();
                    bufferBuilder.vertex(guiGraphics.pose().last().pose(), x + 1, y, 0).color(r,g,b,a).endVertex();
                    bufferBuilder.vertex(guiGraphics.pose().last().pose(), x, y, 0).color(r,g,b,a).endVertex();
                }
            }
        }
        tesselator.end();
        guiGraphics.pose().popPose();
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);
    }
}
