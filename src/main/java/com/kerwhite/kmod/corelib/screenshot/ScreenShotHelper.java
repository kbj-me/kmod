package com.kerwhite.kmod.corelib.screenshot;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
@SuppressWarnings("ALL")
public class ScreenShotHelper
{
    public static class KPixels
    {
        private final List<Integer> pixels;
        private final int height;
        private final int width;
        public KPixels(int height,int width,List<Integer> pixels)
        {
            this.pixels = pixels;
            this.height = height;
            this.width = width;
        }
        public List<Integer> getPixels()
        {
            return pixels;
        }
        public int getWidth()
        {
            return width;
        }
        public int getHeight()
        {
            return height;
        }
    }
    private static final RenderTarget MAIN_RENDER_TARGET = Minecraft.getInstance().getMainRenderTarget();
    private static NativeImage nativeImage;
    private static int height;
    private static int width;
    public static List<Integer> getPixelList()
    {
        AtomicReference<List<Integer>> pixels = new AtomicReference<>();
        ScreenShotHelper.getPixels((kPixels)->
        {
            pixels.set(kPixels.pixels);
        });
        return pixels.get();
    }
    public static KPixels getPixels()
    {
        AtomicReference<KPixels> pixels_ = new AtomicReference<>();
        ScreenShotHelper.getPixels((kPixels)->
        {
            pixels_.set(kPixels);
        });
        return pixels_.get();
    }
    public static void getPixels(Consumer<KPixels> consumer)
    {
        ScreenShotHelper.height = MAIN_RENDER_TARGET.height;
        ScreenShotHelper.width = MAIN_RENDER_TARGET.width;
        RenderSystem.recordRenderCall(() ->
        {
            try
            {
                ScreenShotHelper.nativeImage = new NativeImage(ScreenShotHelper.width,ScreenShotHelper.height,false);
                RenderSystem.bindTexture(MAIN_RENDER_TARGET.getColorTextureId());
                ScreenShotHelper.nativeImage.downloadTexture(0,true);
                ScreenShotHelper.nativeImage.flipY();
                List<Integer> pixels = new ArrayList<>(height*width);
                for (int y = 0; y < height; y++)
                {
                    for (int x = 0; x < width; x++) {
                        int red = nativeImage.getRedOrLuminance(x, y) & 0xFF;
                        int green = nativeImage.getGreenOrLuminance(x, y) & 0xFF;
                        int blue = nativeImage.getBlueOrLuminance(x, y) & 0xFF;
                        int pixel = (red << 16) | (green << 8) | blue;
                        pixels.add(pixel);
                    }
                }
                consumer.accept(new KPixels(ScreenShotHelper.height,ScreenShotHelper.width,pixels));
            }
            finally
            {
                ScreenShotHelper.nativeImage.close();
            }
        });
    }
}
