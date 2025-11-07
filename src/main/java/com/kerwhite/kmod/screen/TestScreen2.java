package com.kerwhite.kmod.screen;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TestScreen2 extends Screen
{
    AtomicInteger pheight;
    AtomicInteger pwitch;
    AtomicReference<List<Integer>> ppixel;
    protected TestScreen2(AtomicInteger i1, AtomicInteger i2, AtomicReference<List<Integer>> l1)
    {
        super(Component.literal("www"));
        this.pheight = i1;
        this.pwitch = i2;
        this.ppixel = l1;
    }
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public void tick()
    {
        ScreenShotHelper.getPixels((kPixels)->
        {
            this.pheight.set(kPixels.getHeight());
            this.pwitch.set(kPixels.getWidth());
            this.ppixel.set(kPixels.getPixels());
        });
        super.tick();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_)
    {
        double scale = Minecraft.getInstance().getWindow().getGuiScale();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale((float) (1.0/scale), (float) (1.0/scale), (float) (1.0/scale));
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        if(this.ppixel.get() != null)
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
                    float r = red / 255.0F; // 1.0F
                    float g = green / 255.0F;   // 0.0F
                    float b = blue / 255.0F;   // 0.0F
                    float a = 1.0f; //
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
